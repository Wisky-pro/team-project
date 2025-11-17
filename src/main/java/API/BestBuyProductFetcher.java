package API;

import java.io.File;
import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.file.Files;
import java.time.LocalDate;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.json.JSONArray;
import org.json.JSONObject;


public class BestBuyProductFetcher extends Fetcher {
    protected final String productURL;
    protected double price;
    protected String name;

    public BestBuyProductFetcher(String URL){
        this.productURL = URL;
    }

    private static String extractProductID(String productURL){
        Pattern pattern = Pattern.compile("/(\\d{7,})");
        Matcher matcher = pattern.matcher(productURL);
        if (matcher.find()) {
            return matcher.group(1);
        } else {
            throw new IllegalArgumentException("Invalid BestBuy URL");
        }
    }

    @Override
    protected HttpResponse<String> sendRequest() {
        String productID = extractProductID(productURL);

        String url = "https://www.bestbuy.ca/api/v2/json/product/" + productID +
                "?currentRegion=ON&include=all&lang=en-CA";

        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(url))
                .header("Accept", "application/json, text/javascript, */*; q=0.01")
                .header("Accept-Language", "en-US,en;q=0.9")
                .header("User-Agent", "Mozilla/5.0 (Macintosh; Intel Mac OS X 14_3_1) " +
                        "AppleWebKit/537.36 (KHTML, like Gecko) Chrome/131.0.0.0 Safari/537.36")
                .header("X-Requested-With", "XMLHttpRequest")
                .GET()
                .build();

        try {
            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

            if (response.statusCode() == 200) {
                return response;
            } else {
                throw new IllegalArgumentException("Failed to fetch product info." + response.statusCode());
            }
        } catch (IOException | InterruptedException e) {
            throw new IllegalArgumentException("Failed to fetch product info, the request is invalid");
        }
    }

    @Override
    protected JSONObject formatData(JSONObject responseJson){

        JSONObject productData = new JSONObject();

        JSONArray historyPrice = new JSONArray();
        JSONObject priceStamp = new JSONObject();
        priceStamp.put("date", LocalDate.now().toString());
        priceStamp.put("price", price);
        historyPrice.put(priceStamp);

        productData.put("productURL", productURL);
        productData.put("name", name);
        productData.put("curPrice", price);
        productData.put("historyPrice", historyPrice);

        return productData;
    }

    @Override
    public void updateJson() {
        String filePath = "priceHistory.json";

        File file = new File(filePath);
        if (!file.exists()) {
            try{
                file.createNewFile();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }

        String content = "";
        if (file.length() > 0) {
            try {
                content = new String(Files.readAllBytes(file.toPath()));
            } catch (IOException e) {
                throw new RuntimeException("Fail to read JSON file", e);
            }
        }
        JSONObject json;
        if (content.isEmpty()) {
            json = new JSONObject();
            json.put("products", new org.json.JSONObject());
        } else {
            json = new JSONObject(content);
        }

        JSONObject responseJson = new JSONObject(sendRequest().body());
        price = responseJson.getDouble("salePrice");
        name = responseJson.getString("name");

        JSONObject products = json.getJSONObject("products");
        if (products.has(productURL)) {
            JSONArray historyPrice = products.getJSONObject(productURL).getJSONArray("historyPrice");
            double lastPrice = historyPrice.getJSONObject(historyPrice.length()-1).getDouble("price");

            if (lastPrice != price){
                JSONObject newPrice = new JSONObject();
                newPrice.put("date", LocalDate.now().toString());
                newPrice.put("price", price);
                historyPrice.put(newPrice);
                products.getJSONObject(productURL).put("curPrice", price);
            }
        } else {
            products.put(productURL, formatData(responseJson));
        }

        try {
            Files.writeString(file.toPath(), json.toString(4));
            System.out.println("update Json successfully");
        } catch (IOException e) {
            throw new RuntimeException("fail to update json", e);
        }
    }
}

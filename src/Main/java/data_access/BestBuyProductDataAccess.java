package data_access;

import API.BestBuyProductFetcher;
import entity.ProductData;
import org.json.JSONObject;
import use_case.AddToCart.ProductDataAccessInterface;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;

public class BestBuyProductDataAccess implements ProductDataAccessInterface {

    private final String filePath;

    public BestBuyProductDataAccess(String filePath) {
        this.filePath = filePath;
    }

    @Override
    public ProductData getByUrl(String productUrl) {
        BestBuyProductFetcher fetcher = new BestBuyProductFetcher(productUrl);
        fetcher.updateJson();

        File file = new File(filePath);
        if (!file.exists() || file.length() == 0) {
            return null;
        }

        String content;
        try {
            content = new String(Files.readAllBytes(file.toPath()));
        } catch (IOException e) {
            throw new RuntimeException("Failed to read product JSON file", e);
        }

        JSONObject root = new JSONObject(content);
        JSONObject products = root.getJSONObject("products");
        if (!products.has(productUrl)) {
            return null;
        }

        JSONObject productJson = products.getJSONObject(productUrl);
        String name = productJson.getString("name");
        double price = productJson.getDouble("curPrice");

        return new ProductData(productUrl, name, price);
    }
}

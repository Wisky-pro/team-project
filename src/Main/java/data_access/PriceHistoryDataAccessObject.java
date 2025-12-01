package data_access;

import entity.PriceHistory;
import org.json.JSONArray;
import org.json.JSONObject;
import use_case.PriceHistory.PriceHistoryDataAccessInterface;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;

/**
 Data access object for view graph (use case 7).
 */
public class PriceHistoryDataAccessObject implements PriceHistoryDataAccessInterface{
    private final Map<String, PriceHistory> historyByUrl = new HashMap<>();

    public PriceHistoryDataAccessObject() {
        try { 
            String filePath = "priceHistory.json";
            String jsonText = Files.readString(Paths.get(filePath));

            JSONObject root = new JSONObject(jsonText);
            JSONObject products = root.getJSONObject("products");

            for (String productUrl: products.keySet()) {
                JSONObject product = products.getJSONObject(productUrl);
                JSONArray productHistory = product.getJSONArray("historyPrice");

                Map<LocalDate, Double> pricePoint = new HashMap<>();
                for (int i = 0; i < productHistory.length(); i++) {
                    JSONObject pricePerDate = productHistory.getJSONObject(i);
                    LocalDate date = LocalDate.parse(pricePerDate.getString("date"));
                    double price = pricePerDate.getDouble("price");
                    pricePoint.put(date, price);
                }

                PriceHistory priceHistory = new PriceHistory(productUrl, pricePoint);
                historyByUrl.put(productUrl, priceHistory);
            }
        } catch (IOException e) {
            throw new RuntimeException("Failed to read priceHistory.json", e);
        }
    }

    @Override
    public PriceHistory getPriceHistory(String productUrl) {
        return historyByUrl.get(productUrl);
    }

    @Override
    public boolean existsByUrl(String productUrl){
        return historyByUrl.containsKey(productUrl);
    }
}

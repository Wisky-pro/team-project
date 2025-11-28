package data_access;

import entity.Commodity;
import use_case.Recommendation.PurchaseRecommendationDataAccessInterface;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;
import org.json.JSONObject;
import org.json.JSONArray;


/**
  Data access object for commodity-related use cases (9 and 10).
 - Actually load data from priceHistory.json
 - Maintain a cart data structure
 */
public class CommodityDataAccessObject implements PurchaseRecommendationDataAccessInterface{

    // Example in-memory storage. You will replace this with real JSON loading.
    private final Map<String, List<Double>> priceHistory = new HashMap<>();
    private final Map<String, Commodity> currentCommodities = new HashMap<>();

    public CommodityDataAccessObject() {
        // This constructor is just a placeholder so AppBuilder can pass a filename.
        try{
        String filePath = "priceHistory.json";
        String jsontext = Files.readString(Paths.get(filePath));
        JSONObject jsonObject = new JSONObject(jsontext);
        JSONObject json = jsonObject.getJSONObject("products");
        for (String url : json.keySet()) {
            JSONObject currProduct  = json.getJSONObject(url);
            String name = currProduct.getString("name");
            Commodity commodity = new Commodity(name,currProduct.getDouble("curPrice"));
            this.currentCommodities.put(name,commodity);
            List<Double> pricelist = new ArrayList<>();
            JSONArray jsonArray = currProduct.getJSONArray("historyPrice");
            for (int i = 0; i < jsonArray.length(); i++) {
                pricelist.add(jsonArray.getJSONObject(i).getDouble("price"));
            }
            this.priceHistory.put(name,pricelist);
        }
        } catch (IOException e) {
            throw new RuntimeException("Falied to read JSON file");
        }

    }

    // --------- Use Case 9 methods ---------
    @Override
    public List<Double> getPriceHistory(String commodityName) {
        return priceHistory.get(commodityName);
    }

    @Override
    public double getCurrentPrice(String commodityName) {
        Commodity commodity = currentCommodities.get(commodityName);
        if (commodity == null) {
            // You can also throw an exception here if you prefer.
            return -1.0;
        }
        return commodity.getCurrentPrice();
    }

    @Override
    public boolean commodityExists(String commodityName) {
        return currentCommodities.containsKey(commodityName);
    }

}
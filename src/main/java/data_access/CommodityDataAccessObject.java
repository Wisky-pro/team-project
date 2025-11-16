package data_access;

import entity.Commodity;
import use_case.Recommendation.PurchaseRecommendationDataAccessInterface;

import java.util.*;

/**
  Data access object for commodity-related use cases (9 and 10).

 * TODO:
 - Actually load data from priceHistory.json
 - Maintain a cart data structure
 */
public class CommodityDataAccessObject implements PurchaseRecommendationDataAccessInterface{

    // Example in-memory storage. You will replace this with real JSON loading.
    private final Map<String, List<Double>> priceHistory = new HashMap<>();
    private final Map<String, Commodity> currentCommodities = new HashMap<>();
    private final List<Commodity> cart = new ArrayList<>();

    public CommodityDataAccessObject(String priceHistoryFilename) {
        // TODO: read priceHistory.json and initialize priceHistory + currentCommodities
        // This constructor is just a placeholder so AppBuilder can pass a filename.
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
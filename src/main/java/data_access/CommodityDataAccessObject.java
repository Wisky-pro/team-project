package data_access;

import use_case.AddToCart.ProductDataAccessInterface;
import use_case.Recommendation.PurchaseRecommendationDataAccessInterface;
import entity.ProductData;
import entity.Commodity;

import java.io.*;
import java.util.*;

public class CommodityDataAccessObject implements
        ProductDataAccessInterface,
        PurchaseRecommendationDataAccessInterface,
        Serializable {

    private final String filename;
    private Map<String, Commodity> currentCommodities;
    private Map<String, List<Double>> priceHistory;

    public CommodityDataAccessObject(String filename) {
        this.filename = filename;
        this.currentCommodities = new HashMap<>();
        this.priceHistory = new HashMap<>();
        loadData();
    }

    // --- Load data from file ---
    @SuppressWarnings("unchecked")
    private void loadData() {
        File file = new File(filename);
        if (!file.exists()) return;

        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(file))) {
            currentCommodities = (Map<String, Commodity>) ois.readObject();
            priceHistory = (Map<String, List<Double>>) ois.readObject();
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
            currentCommodities = new HashMap<>();
            priceHistory = new HashMap<>();
        }
    }

    // --- Save data to file ---
    private void saveData() {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(filename))) {
            oos.writeObject(currentCommodities);
            oos.writeObject(priceHistory);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // ---------------- ProductDataAccessInterface ----------------
    @Override
    public ProductData getByUrl(String productUrl) {
        Commodity commodity = currentCommodities.get(productUrl);
        if (commodity == null) return null;
        return new ProductData(productUrl, commodity.getName(), commodity.getCurrentPrice());
    }

    // ---------------- PurchaseRecommendationDataAccessInterface ----------------
    @Override
    public List<Double> getPriceHistory(String commodityName) {
        return priceHistory.getOrDefault(commodityName, new ArrayList<>());
    }

    @Override
    public double getCurrentPrice(String commodityName) {
        Commodity commodity = currentCommodities.get(commodityName);
        if (commodity == null) return -1;
        return commodity.getCurrentPrice();
    }

    @Override
    public boolean commodityExists(String commodityName) {
        return currentCommodities.containsKey(commodityName);
    }

    // ---------------- Additional methods ----------------
    public void addOrUpdateCommodity(Commodity commodity) {
        currentCommodities.put(commodity.getName(), commodity);
        priceHistory.putIfAbsent(commodity.getName(), new ArrayList<>());
        priceHistory.get(commodity.getName()).add(commodity.getCurrentPrice());
        saveData();
    }

    public Map<String, Commodity> getAllCommodities() {
        return currentCommodities;
    }
}

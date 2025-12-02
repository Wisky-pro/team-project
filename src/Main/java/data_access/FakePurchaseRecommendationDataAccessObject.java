package data_access;

import use_case.Recommendation.PurchaseRecommendationDataAccessInterface;

import java.util.ArrayList;
import java.util.List;

/**
 Fake implementation for Use Case 9.
 */
public class FakePurchaseRecommendationDataAccessObject
        implements PurchaseRecommendationDataAccessInterface {

    @Override
    public List<Double> getPriceHistory(String commodityName) {
        List<Double> history = new ArrayList<>();
        history.add(900.0);
        history.add(1000.0);
        history.add(1100.0);
        return history;
    }

    @Override
    public double getCurrentPrice(String commodityName) {
        return 1200.0;
    }

    @Override
    public boolean commodityExists(String commodityName) {
        return "Laptop".equalsIgnoreCase(commodityName);
    }
}
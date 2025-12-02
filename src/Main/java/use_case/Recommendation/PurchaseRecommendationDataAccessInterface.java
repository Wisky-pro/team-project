package use_case.Recommendation;

import java.util.List;

/**
  Data access interface for Use Case 9.
  Implemented in the data_access layer (e.g., CommodityDataAccessObject).
 */
public interface PurchaseRecommendationDataAccessInterface {

    List<Double> getPriceHistory(String commodityName);

    double getCurrentPrice(String commodityName);

    boolean commodityExists(String commodityName);
}
package use_case.Recommendation;

import java.util.List;

/**
  Data access interface for Use Case 9.
  Implemented in the data_access layer (e.g., CommodityDataAccessObject).
 */
public interface PurchaseRecommendationDataAccessInterface {

    /**
     Returns the history prices for this commodity, read from priceHistory.json.
     */
    List<Double> getPriceHistory(String commodityName);

    /**
     Returns the current price of the commodity.
     It can be from the same JSON file or some in-memory data.
     */
    double getCurrentPrice(String commodityName);

    /**
     Returns true if this commodity exists in the data source.
     */
    boolean commodityExists(String commodityName);
}
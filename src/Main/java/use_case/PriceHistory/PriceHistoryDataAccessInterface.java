package use_case.PriceHistory;

import entity.PriceHistory;

/**
 Data access interface for Use Case 7.
 Implemented in the data_access layer (e.g., PriceHistoryDataAccessObject).
 */
public interface PriceHistoryDataAccessInterface {

    PriceHistory getPriceHistory(String productUrl);
}

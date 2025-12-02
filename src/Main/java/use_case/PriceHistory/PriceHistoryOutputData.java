package use_case.PriceHistory;

import java.time.LocalDate;
import java.util.List;

/**
 Output data for Use Case 7.
 It contains the dates and prices.
 */
public class PriceHistoryOutputData {
    private final List<LocalDate> dates;
    private final List<Double> prices;
    private final String productUrl;

    public PriceHistoryOutputData(List<LocalDate> dates, List<Double> prices, String productUrl) {
        this.dates = dates;
        this.prices = prices;
        this.productUrl = productUrl;
    }

    public List<LocalDate> getDates() {
        return dates;
    }

    public List<Double> getPrices() {
        return prices;
    }

    public String getProductUrl() {
        return productUrl;
    }
}

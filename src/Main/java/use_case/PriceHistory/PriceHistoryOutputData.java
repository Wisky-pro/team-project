package use_case.PriceHistory;

import java.time.LocalDate;
import java.util.List;

/**
 Output data for Use Case 7.
 Contains the list of dates, list of corresponding prices, and identifying information.
 */
public class PriceHistoryOutputData {
    private final List<LocalDate> dates;
    private final List<Double> prices;
    private final String productUrl;
    private final String productName;

    public PriceHistoryOutputData(List<LocalDate> dates, List<Double> prices, String productUrl, String productName) {
        this.dates = dates;
        this.prices = prices;
        this.productUrl = productUrl;
        this.productName = productName;
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

    public String getProductName() { return productName; }
}

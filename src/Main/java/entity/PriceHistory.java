package entity;

import java.time.LocalDate;
import java.util.Map;

/*
 * An entity that represents the price history of a single product.
 */
public class PriceHistory {
    private final String productUrl;
    private final String productName;
    private final Map<LocalDate, Double> priceHistory;

    public PriceHistory(String productUrl, String productName, Map<LocalDate, Double> priceHistory) {
        this.productUrl = productUrl;
        this.productName = productName;
        this.priceHistory = priceHistory;
    }

    public String getProductUrl() {
        return productUrl;
    }

    public String getProductName() {
        return productName;
    }

    public Map<LocalDate, Double> getPriceHistory() {
        return priceHistory;
    }
}

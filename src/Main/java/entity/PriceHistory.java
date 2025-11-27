package entity;

import java.time.LocalDate;
import java.util.Map;

/*
 * An entity that represents the price history of a single product.
 */
public class PriceHistory {
    private final String productUrl;
    private final Map<LocalDate, Double> priceHistory;

    public PriceHistory(String productUrl, Map<LocalDate, Double> priceHistory) {
        this.productUrl = productUrl;
        this.priceHistory = priceHistory;
    }

    public String getProductUrl() {
        return productUrl;
    }
    public Map<LocalDate, Double> getPriceHistory() {
        return priceHistory;
    }

    public Double getPriceForDate(LocalDate date) {
        return priceHistory.get(date);
    }
}

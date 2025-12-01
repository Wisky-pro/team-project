package use_case.PriceHistory;

/**
 Input data for Use Case 7.
 For now, we only need the productUrl that user selects.
 */
public class PriceHistoryInputData {

    private final String productUrl;

    public PriceHistoryInputData(String productUrl) {
        this.productUrl = productUrl;
    }

    public String getProductUrl() {
        return productUrl;
    }
}

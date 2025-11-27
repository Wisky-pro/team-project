package use_case.PriceHistory;

/**
 Input data for Use Case 7.
 For now we only need the productUrl that user selects.
 */
public class PriceHistoryInputData {
    private final String productUrl;
    private final int numDaysView;

    public PriceHistoryInputData(String productUrl, int numDaysView) {
        this.productUrl = productUrl;
        this.numDaysView = numDaysView;
    }

    public String getProductUrl() {
        return productUrl;
    }

    public int getNumDaysView() {
        return numDaysView;
    }
}

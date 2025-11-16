package use_case.Recommendation;

/**
  Output data for Use Case 9.
  It contains the current price, mean price and a human-readable suggestion.
 */
public class PurchaseRecommendationOutputData {

    private final String commodityName;
    private final double currentPrice;
    private final double meanPrice;
    private final String suggestionMessage;

    public PurchaseRecommendationOutputData(String commodityName,
                                            double currentPrice,
                                            double meanPrice,
                                            String suggestionMessage) {
        this.commodityName = commodityName;
        this.currentPrice = currentPrice;
        this.meanPrice = meanPrice;
        this.suggestionMessage = suggestionMessage;
    }

    public String getCommodityName() {
        return commodityName;
    }

    public double getCurrentPrice() {
        return currentPrice;
    }

    public double getMeanPrice() {
        return meanPrice;
    }

    public String getSuggestionMessage() {
        return suggestionMessage;
    }
}
package use_case.Recommendation;

/**
  Input data for Use Case 9.
  For now, we only need the commodity name that user selects.
 */
public class PurchaseRecommendationInputData {

    private final String commodityName;

    public PurchaseRecommendationInputData(String commodityName) {
        this.commodityName = commodityName;
    }

    public String getCommodityName() {
        return commodityName;
    }
}
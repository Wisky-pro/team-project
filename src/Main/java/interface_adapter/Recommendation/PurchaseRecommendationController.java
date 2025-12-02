package interface_adapter.Recommendation;

import use_case.Recommendation.PurchaseRecommendationInputBoundary;
import use_case.Recommendation.PurchaseRecommendationInputData;

/**
 Controller for Use Case 9.
 Called by the DashboardView when user clicks "Get Recommendation" button.
 */
public class PurchaseRecommendationController {

    private final PurchaseRecommendationInputBoundary interactor;

    public PurchaseRecommendationController(PurchaseRecommendationInputBoundary interactor) {
        this.interactor = interactor;
    }

    public void getRecommendation(String commodityName) {
        PurchaseRecommendationInputData inputData =
                new PurchaseRecommendationInputData(commodityName);
        interactor.execute(inputData);
    }
}

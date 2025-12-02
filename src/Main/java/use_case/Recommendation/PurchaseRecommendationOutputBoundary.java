package use_case.Recommendation;

/**
 Output boundary for Use Case 9.
 */
public interface PurchaseRecommendationOutputBoundary {

    void prepareSuccessView(PurchaseRecommendationOutputData outputData);

    void prepareFailView(String errorMessage);
}
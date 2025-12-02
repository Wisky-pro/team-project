package use_case.Recommendation;

/**
 Output boundary for Use Case 9.
 */
public interface PurchaseRecommendationOutputBoundary {

    /**
     * Called when recommendation is successfully generated.
     */
    void prepareSuccessView(PurchaseRecommendationOutputData outputData);

    /**
     Called when something goes wrong (e.g., commodity not found).
     */
    void prepareFailView(String errorMessage);
}
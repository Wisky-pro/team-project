package use_case.Recommendation;

/**
  Input boundary for Use Case 9: Purchase recommendations.
 */
public interface PurchaseRecommendationInputBoundary {

    /**
     Generate a recommendation for the given commodity.
     @param inputData input data containing commodity name, etc.
     */
    void execute(PurchaseRecommendationInputData inputData);
}
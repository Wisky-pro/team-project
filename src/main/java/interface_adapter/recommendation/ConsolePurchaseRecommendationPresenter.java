package interface_adapter.recommendation;

import use_case.Recommendation.PurchaseRecommendationOutputBoundary;
import use_case.Recommendation.PurchaseRecommendationOutputData;

/**
 * 暂时用于测试的简单 Presenter：只打印到控制台。
 */
public class ConsolePurchaseRecommendationPresenter implements PurchaseRecommendationOutputBoundary {

    @Override
    public void prepareSuccessView(PurchaseRecommendationOutputData outputData) {
        System.out.println("=== Recommendation Success ===");
        System.out.println("Commodity: " + outputData.getCommodityName());
        System.out.println("Current price: " + outputData.getCurrentPrice());
        System.out.println("Mean price: " + outputData.getMeanPrice());
        System.out.println("Suggestion: " + outputData.getSuggestionMessage());
    }

    @Override
    public void prepareFailView(String errorMessage) {
        System.out.println("=== Recommendation Failed ===");
        System.out.println(errorMessage);
    }
}
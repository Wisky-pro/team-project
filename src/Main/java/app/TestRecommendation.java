package app;

import data_access.FakePurchaseRecommendationDataAccessObject;
import interface_adapter.Recommendation.ConsolePurchaseRecommendationPresenter;
import use_case.Recommendation.*;

public class TestRecommendation {
    public static void main(String[] args) {
        PurchaseRecommendationDataAccessInterface dataAccess =
                new FakePurchaseRecommendationDataAccessObject();
        
        PurchaseRecommendationOutputBoundary presenter =
                new ConsolePurchaseRecommendationPresenter();

        PurchaseRecommendationInputBoundary interactor =
                new PurchaseRecommendationInteractor(dataAccess, presenter);

        PurchaseRecommendationInputData inputData =
                new PurchaseRecommendationInputData("Laptop");

        interactor.execute(inputData);
    }
}
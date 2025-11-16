package use_case.Recommendation;

import java.util.List;

/**
 Interactor for Use Case 9: Purchase recommendations.
 */
public class PurchaseRecommendationInteractor implements PurchaseRecommendationInputBoundary {

    private final PurchaseRecommendationDataAccessInterface dataAccess;
    private final PurchaseRecommendationOutputBoundary presenter;

    public PurchaseRecommendationInteractor(PurchaseRecommendationDataAccessInterface dataAccess,
                                            PurchaseRecommendationOutputBoundary presenter) {
        this.dataAccess = dataAccess;
        this.presenter = presenter;
    }

    @Override
    public void execute(PurchaseRecommendationInputData inputData) {
        String name = inputData.getCommodityName();

        if (!dataAccess.commodityExists(name)) {
            presenter.prepareFailView("Commodity " + name + " not found.");
            return;
        }

        List<Double> history = dataAccess.getPriceHistory(name);
        if (history == null || history.isEmpty()) {
            presenter.prepareFailView("No price history for " + name + ".");
            return;
        }

        double sum = 0.0;
        for (double p : history) {
            sum += p;
        }
        double mean = sum / history.size();

        double currentPrice = dataAccess.getCurrentPrice(name);

        String suggestion;
        // According to your spec: higher than mean -> recommend to buy
        // equal or lower -> not recommend
        if (currentPrice > mean) {
            suggestion = "Price is higher than mean, recommend to buy.";
        } else {
            suggestion = "Price is equal or lower than mean, NOT recommend to buy.";
        }

        PurchaseRecommendationOutputData outputData =
                new PurchaseRecommendationOutputData(name, currentPrice, mean, suggestion);
        presenter.prepareSuccessView(outputData);
    }
}
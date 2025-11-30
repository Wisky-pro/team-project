package use_case.Recommendation;

import java.util.Collections;
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

        List<Double> history = getList(name);

        double sum = 0.0;
        double mean = getMean(history, sum);

        double currentPrice = dataAccess.getCurrentPrice(name);

        PurchaseRecommendationOutputData outputData = getPurchaseRecommendationOutputData(currentPrice, mean, name);
        presenter.prepareSuccessView(outputData);
    }

    private static double getMean(List<Double> history, double sum) {
        for (double p : history) {
            sum += p;
        }
        return sum / history.size();
    }

    private List<Double> getList(String name) {
        if (!dataAccess.commodityExists(name)) {
            presenter.prepareFailView("Commodity " + name + " not found.");
            return Collections.emptyList();
        }

        List<Double> history = dataAccess.getPriceHistory(name);
        if (history == null || history.isEmpty()) {
            presenter.prepareFailView("No price history for " + name + ".");
            return Collections.emptyList();
        }
        return history;
    }

    private static PurchaseRecommendationOutputData getPurchaseRecommendationOutputData(double currentPrice, double mean, String name) {
        String suggestion;
        // According to your spec: higher than mean -> recommend to buy
        // equal or lower -> not recommend
        if (currentPrice < mean) {
            suggestion = "Price is lower than mean, recommend to buy.";
        } else {
            suggestion = "Price is equal or higher than mean, NOT recommend to buy.";
        }

        return new PurchaseRecommendationOutputData(name, currentPrice, mean, suggestion);
    }
}
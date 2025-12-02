package interface_adapter.Recommendation;

import interface_adapter.Dashboard.DashboardViewModel;
import use_case.Recommendation.PurchaseRecommendationOutputBoundary;
import use_case.Recommendation.PurchaseRecommendationOutputData;

/**
 Presenter for Use Case 9.
 It converts output data to a simple message and updates DashboardViewModel.
 */
public class PurchaseRecommendationPresenter implements PurchaseRecommendationOutputBoundary {

    private final DashboardViewModel dashboardVM;

    public PurchaseRecommendationPresenter(DashboardViewModel dashboardVM) {
        this.dashboardVM = dashboardVM;
    }

    @Override
    public void prepareSuccessView(PurchaseRecommendationOutputData outputData) {
        String msg = "Commodity: " + outputData.getCommodityName()
                + "\nCurrent price: " + outputData.getCurrentPrice()
                + "\nMean price: " + outputData.getMeanPrice()
                + "\nSuggestion: " + outputData.getSuggestionMessage();

        dashboardVM.setMessage(msg);
        dashboardVM.firePropertyChanged();
    }

    @Override
    public void prepareFailView(String errorMessage) {
        dashboardVM.setMessage("Recommendation error: " + errorMessage);
        dashboardVM.firePropertyChanged();
    }
}
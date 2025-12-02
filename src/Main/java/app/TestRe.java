package app;

import data_access.CommodityDataAccessObject;
import interface_adapter.Dashboard.DashboardViewModel;
import interface_adapter.Recommendation.PurchaseRecommendationController;
import interface_adapter.Recommendation.PurchaseRecommendationPresenter;
import use_case.Recommendation.*;
import view.RecommendationView;

import javax.swing.*;

class TestRecommendationGuiMain {
    public static void main(String[] args) {
        // 1. ViewModel
        DashboardViewModel dashboardVM = new DashboardViewModel();

        // 2. DataAccess
        PurchaseRecommendationController controller = getPurchaseRecommendationController(dashboardVM);

        // 6. View
        RecommendationView view = new RecommendationView(dashboardVM, controller);

        JFrame frame = new JFrame("Test Recommendation");
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        frame.setContentPane(view);
        frame.pack();
        frame.setVisible(true);
    }

    private static PurchaseRecommendationController getPurchaseRecommendationController(DashboardViewModel dashboardVM) {
        PurchaseRecommendationDataAccessInterface dataAccess =
                new CommodityDataAccessObject();

        // 3. Presenter
        PurchaseRecommendationOutputBoundary presenter =
                new PurchaseRecommendationPresenter(dashboardVM);

        // 4. Interactor
        PurchaseRecommendationInputBoundary interactor =
                new PurchaseRecommendationInteractor(dataAccess, presenter);

        // 5. Controller
        return new PurchaseRecommendationController(interactor);
    }
}
package app;

import data_access.CommodityDataAccessObject;
import interface_adapter.Dashboard.DashboardViewModel;
import interface_adapter.Recommendation.PurchaseRecommendationController;
import interface_adapter.Recommendation.PurchaseRecommendationPresenter;
import use_case.Recommendation.*;
import view.DashboardViewForTest;

import javax.swing.*;

public class TestRecommendationGuiMain {
    public static void main(String[] args) {

        DashboardViewModel dashboardVM = new DashboardViewModel();

        PurchaseRecommendationController controller = getPurchaseRecommendationController(dashboardVM);

        DashboardViewForTest view = new DashboardViewForTest(dashboardVM, controller);

        JFrame frame = new JFrame("Test Recommendation");
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        frame.setContentPane(view);
        frame.pack();
        frame.setVisible(true);
    }

    private static PurchaseRecommendationController getPurchaseRecommendationController(DashboardViewModel dashboardVM) {
        PurchaseRecommendationDataAccessInterface dataAccess =
                new CommodityDataAccessObject();

        PurchaseRecommendationOutputBoundary presenter =
                new PurchaseRecommendationPresenter(dashboardVM);

        PurchaseRecommendationInputBoundary interactor =
                new PurchaseRecommendationInteractor(dataAccess, presenter);

        return new PurchaseRecommendationController(interactor);
    }
}
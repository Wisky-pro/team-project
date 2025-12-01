package app;

import data_access.PriceHistoryDataAccessObject;
import interface_adapter.PriceHistory.PriceHistoryController;
import interface_adapter.PriceHistory.PriceHistoryPresenter;
import interface_adapter.PriceHistory.PriceHistoryViewModel;
import use_case.PriceHistory.PriceHistoryDataAccessInterface;
import use_case.PriceHistory.PriceHistoryInputBoundary;
import use_case.PriceHistory.PriceHistoryInteractor;
import view.PriceHistoryGraphView;

import javax.swing.*;

class PriceHistoryTestApp {
    public static void main(String[] args) {
        PriceHistoryViewModel viewModel = new PriceHistoryViewModel();
        PriceHistoryPresenter presenter = new PriceHistoryPresenter(viewModel);

        PriceHistoryDataAccessInterface dataAccess = new PriceHistoryDataAccessObject();
        PriceHistoryInputBoundary interactor =
                new PriceHistoryInteractor(dataAccess, presenter);

        PriceHistoryController controller = new PriceHistoryController(interactor);

        String testUrl = "https://www.bestbuy.ca/en-ca/product/samsung-galaxy-a16-5g-128gb-blue-black-unlocked/18896891";
        controller.viewPriceHistory(testUrl);

        SwingUtilities.invokeLater(() -> {
            JFrame frame = new JFrame("Price History Test");
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.setContentPane(new PriceHistoryGraphView(viewModel));
            frame.pack();
            frame.setVisible(true);
        });
    }
}

package interface_adapter.PriceHistory;

import use_case.PriceHistory.PriceHistoryInputBoundary;
import use_case.PriceHistory.PriceHistoryInputData;

public class PriceHistoryController {
    private final PriceHistoryInputBoundary interactor;

    public PriceHistoryController(PriceHistoryInputBoundary interactor) {
        this.interactor = interactor;
    }

    public void viewPriceHistory(String productUrl) {
        PriceHistoryInputData inputData = new PriceHistoryInputData(productUrl); //viewing the price graph of the last 10 days.
        interactor.execute(inputData);
    }
}

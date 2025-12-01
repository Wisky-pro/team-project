package use_case.PriceHistory;

import entity.PriceHistory;

import java.time.LocalDate;
import java.util.*;
import java.util.List;

/**
 Interactor for Use Case 7: View History Price Graph
 */
public class PriceHistoryInteractor implements PriceHistoryInputBoundary {
    private final PriceHistoryDataAccessInterface dataAccess;
    private final PriceHistoryOutputBoundary priceHistoryPresenter;

    public PriceHistoryInteractor(PriceHistoryDataAccessInterface dataAccess,
                                  PriceHistoryOutputBoundary priceHistoryPresenter) {
        this.dataAccess = dataAccess;
        this.priceHistoryPresenter = priceHistoryPresenter;
    }

    @Override
    public void execute(PriceHistoryInputData InputData) {
        String productUrl = InputData.getProductUrl();
        int numDays = InputData.getNumDaysView();

        PriceHistory history = dataAccess.getPriceHistory(productUrl);
        Map<LocalDate, Double> priceHistory = history.getPriceHistory();
        LocalDate today = LocalDate.now();

        List<LocalDate> dates = new ArrayList<>();
        List<Double> prices = new ArrayList<>();

        Double lastPrice = null; //if data has a gap between days, then last known price will be used

        for(int i = numDays - 1; i >= 0; i--) {
            LocalDate day = today.minusDays(i);
            dates.add(day);

            Double price = priceHistory.get(day);
            if(price != null) {
                lastPrice = price;
                prices.add(price);
            }else{
                prices.add(lastPrice);
            }
        }

        PriceHistoryOutputData outputData = new PriceHistoryOutputData(dates, prices, productUrl);
    }
}

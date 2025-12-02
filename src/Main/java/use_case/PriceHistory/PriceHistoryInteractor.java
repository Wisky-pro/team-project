package use_case.PriceHistory;

import entity.PriceHistory;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.*;

/**
 Interactor for Use Case 7: View History Price Graph
 */
public class PriceHistoryInteractor implements PriceHistoryInputBoundary {

    private static final int MINIMUM_NUM_DAY_VIEW = 7;

    private final PriceHistoryDataAccessInterface dataAccess;
    private final PriceHistoryOutputBoundary priceHistoryOutputBoundary;

    public PriceHistoryInteractor(PriceHistoryDataAccessInterface dataAccess,
                                  PriceHistoryOutputBoundary priceHistoryOutputBoundary) {
        this.dataAccess = dataAccess;
        this.priceHistoryOutputBoundary = priceHistoryOutputBoundary;
    }

    @Override
    public void execute(PriceHistoryInputData inputData) {
        String productUrl = inputData.getProductUrl();

        PriceHistory history = dataAccess.getPriceHistory(productUrl);
        if(history == null) {
            priceHistoryOutputBoundary.prepareFailView("No Price History Found for this product.");
            return;
        }

        Map<LocalDate, Double> priceHistory = history.getPriceHistory();
        if(priceHistory == null || priceHistory.isEmpty()) {
            priceHistoryOutputBoundary.prepareFailView("No Price Data Available for this product.");
            return;
        }

        PriceHistoryOutputData outputData = buildOutputData(productUrl, history, priceHistory);
        priceHistoryOutputBoundary.prepareSuccessView(outputData);
    }

    private PriceHistoryOutputData buildOutputData(String productUrl,
                                                   PriceHistory history,
                                                   Map<LocalDate, Double> priceHistory){
        LocalDate today = LocalDate.now();
        LocalDate minDate = Collections.min(priceHistory.keySet());

        int daysBetween = Math.toIntExact(ChronoUnit.DAYS.between(minDate, today)) + 1;
        int numView = Math.max(daysBetween, MINIMUM_NUM_DAY_VIEW);

        List<LocalDate> dates = new ArrayList<>();
        List<Double> prices = new ArrayList<>();
        String productName = history.getProductName();

        // if data has a gap between days, then last known price will be used.
        Double lastPrice = null;

        for (int i = numView - 1; i >= 0; i--) {
            LocalDate day = today.minusDays(i);
            dates.add(day);

            Double price = priceHistory.get(day);
            if (price != null) {
                lastPrice = price;
                prices.add(price);
            } else {
                prices.add(lastPrice);
            }
        }

        if (dates.isEmpty() || prices.isEmpty()) {
            priceHistoryOutputBoundary.prepareFailView("Was not able to collect price data.");
            return null;
        }

        return new PriceHistoryOutputData(dates, prices, productUrl, productName);
    }
}
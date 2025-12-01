package use_case.PriceHistory;

import entity.PriceHistory;

import java.awt.*;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.*;
import java.util.List;

/**
 Interactor for Use Case 7: View History Price Graph
 */
public class PriceHistoryInteractor implements PriceHistoryInputBoundary {
    private final PriceHistoryDataAccessInterface dataAccess;
    private final PriceHistoryOutputBoundary priceHistoryOutputBoundary;

    public PriceHistoryInteractor(PriceHistoryDataAccessInterface dataAccess,
                                  PriceHistoryOutputBoundary priceHistoryOutputBoundary) {
        this.dataAccess = dataAccess;
        this.priceHistoryOutputBoundary = priceHistoryOutputBoundary;
    }

    @Override
    public void execute(PriceHistoryInputData InputData) {
        String productUrl = InputData.getProductUrl();

        PriceHistory history = dataAccess.getPriceHistory(productUrl);
        Map<LocalDate, Double> priceHistory = history.getPriceHistory();
        LocalDate today = LocalDate.now();


        LocalDate minDate = Collections.min(priceHistory.keySet());
        int checkTemp = Math.max(Math.toIntExact(ChronoUnit.DAYS.between(minDate, today)) + 1, 10);
        System.out.println(checkTemp);

        List<LocalDate> dates = new ArrayList<>();
        List<Double> prices = new ArrayList<>();
        String productName = history.getProductName();

        Double lastPrice = null; // if data has a gap between days, then last known price will be used

        for (int i = checkTemp - 1; i >= 0; i--) {
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
            return;
        }

        PriceHistoryOutputData outputData = new PriceHistoryOutputData(dates, prices, productUrl, productName);
        priceHistoryOutputBoundary.prepareSuccessView(outputData);
    }
}

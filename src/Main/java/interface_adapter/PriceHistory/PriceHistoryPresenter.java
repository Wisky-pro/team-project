package interface_adapter.PriceHistory;

import use_case.PriceHistory.PriceHistoryOutputBoundary;
import use_case.PriceHistory.PriceHistoryOutputData;

import java.awt.*;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class PriceHistoryPresenter implements PriceHistoryOutputBoundary {
    private final PriceHistoryViewModel viewModel;

    public PriceHistoryPresenter(PriceHistoryViewModel viewModel) {
        this.viewModel = viewModel;
    }

    @Override
    public void prepareSuccessView(PriceHistoryOutputData outputData){
        List<Date> dateList = new ArrayList<>();
        for(var localDate : outputData.getDates()){
            LocalDate date = localDate.plusDays(1);
            Date asDate = Date.from(date.atStartOfDay(ZoneId.of("UTC")).toInstant());
            dateList.add(asDate);
        }

        viewModel.setDates(dateList);
        viewModel.setPrices(outputData.getPrices());
        viewModel.setProductName(outputData.getProductName());
        viewModel.setProductUrl(outputData.getProductUrl());
    }

    @Override
    public void prepareFailView(String errorMessage){
        viewModel.setMessage(errorMessage);
    }
}

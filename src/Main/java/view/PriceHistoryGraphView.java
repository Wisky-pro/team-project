package view;

import interface_adapter.PriceHistory.PriceHistoryViewModel;
import use_case.PriceHistory.PriceHistoryDataAccessInterface;

import org.knowm.xchart.XChartPanel;
import org.knowm.xchart.XYChartBuilder;
import org.knowm.xchart.XYChart;

import javax.swing.*;
import java.awt.*;
import java.time.LocalDate;
import java.util.*;

import entity.*;

public class PriceHistoryGraphView extends JPanel {

    public PriceHistoryGraphView(PriceHistoryViewModel viewModel, PriceHistoryDataAccessInterface dataAccess, String url) {
        PriceHistory data = dataAccess.getPriceHistory(url);
        Map<LocalDate, Double> dates = data.getPriceHistory();

        Set<LocalDate> datesSet = dates.keySet();
        ArrayList<LocalDate> datesList = new ArrayList<>(datesSet);

        Collection<Double> pricesCollection = dates.values();
        ArrayList<Double> pricesList = new ArrayList<>(pricesCollection);

        XYChart graph = new XYChartBuilder()
                .width(1200).height(500).title("Price History: " + viewModel.getProductName())
                .xAxisTitle("Date").yAxisTitle("Price ($CAD)").build();

        setLayout(new BorderLayout());

        graph.addSeries("Product Price", datesList, pricesList);

        graph.getStyler().setToolTipsEnabled(true);
        graph.getStyler().setDatePattern("MMM d");
        graph.getStyler().setPlotContentSize(0.78);

        add(new XChartPanel<>(graph), BorderLayout.CENTER);
    }   
}

package view;

import interface_adapter.PriceHistory.PriceHistoryViewModel;
import org.knowm.xchart.XChartPanel;
import org.knowm.xchart.XYChartBuilder;
import org.knowm.xchart.XYChart;

import javax.swing.*;
import java.awt.*;

public class PriceHistoryGraphView extends JPanel {
    private final PriceHistoryViewModel viewModel;
    private String productUrl;

    public PriceHistoryGraphView(PriceHistoryViewModel viewModel) {
        this.viewModel = viewModel;

        XYChart graph = new XYChartBuilder()
                .width(1200).height(500).title("Price History: " + viewModel.getProductName())
                .xAxisTitle("Date").yAxisTitle("Price ($CAD)").build();

        graph.addSeries("Product Price", viewModel.getDates(), viewModel.getPrices());

        graph.getStyler().setToolTipsEnabled(true);
        graph.getStyler().setDatePattern("MMM d");
        graph.getStyler().setPlotContentSize(0.78);

        setLayout(new BorderLayout());
        add(new XChartPanel<>(graph), BorderLayout.CENTER);
    }
}
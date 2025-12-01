package view;

import interface_adapter.PriceHistory.PriceHistoryViewModel;
import org.knowm.xchart.XChartPanel;
import org.knowm.xchart.XYChartBuilder;
import org.knowm.xchart.XYChart;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class PriceHistoryGraphView extends JPanel {
    private final PriceHistoryViewModel viewModel;
    private final List <Date> allDates;
    private final List <Double> allPrices;
    private final XYChart graph;
    private final XChartPanel<XYChart> graphPanel;

    public PriceHistoryGraphView(PriceHistoryViewModel viewModel) {
        this.viewModel = viewModel;
        this.allDates = viewModel.getDates();
        this.allPrices = viewModel.getPrices();

        this.graph = new XYChartBuilder()
                .width(1200).height(500).title("Price History: " + viewModel.getProductName())
                .xAxisTitle("Date").yAxisTitle("Price ($CAD)").build();

        graph.addSeries("Product Price", allDates, allPrices);

        graph.getStyler().setToolTipsEnabled(true);
        graph.getStyler().setDatePattern("MMM d");
        graph.getStyler().setPlotContentSize(0.78);

        setLayout(new BorderLayout());
        this.graphPanel = new XChartPanel<>(graph);

        JPanel buttonPanel = new JPanel();
        JButton last7Button = new JButton("7d");
        JButton last30Button = new JButton("30d");
        JButton lastAllButton = new JButton("Max");

        buttonPanel.add(last7Button);
        buttonPanel.add(last30Button);
        buttonPanel.add(lastAllButton);

        last7Button.addActionListener(e -> showLastNDays(7));
        last30Button.addActionListener(e -> showLastNDays(30));
        lastAllButton.addActionListener(e -> showAllDays());

        add(buttonPanel, BorderLayout.SOUTH);
        add(graphPanel, BorderLayout.NORTH);
    }

    private void showLastNDays(int days) {
        if(allDates.size() < days) {
            showAllDays();
            return;
        }
        Date cutoff = allDates.get(allDates.size() - days);
        List<Date> allDates = viewModel.getDates();
        List<Double> allPrices = viewModel.getPrices();

        List<Date> filteredDates = new ArrayList<>();
        List<Double> filteredPrices = new ArrayList<>();

        for (int i=allDates.size() - 1; i >= 0; i--){
            if(allDates.get(i).before(cutoff)){
                break;
            }
            filteredDates.add(allDates.get(i));
            filteredPrices.add(allPrices.get(i));
        }

        updateGraph(filteredDates, filteredPrices);
    }

    private void showAllDays() {
        updateGraph(allDates, allPrices);
    }

    private void updateGraph(List<Date> days, List<Double> prices) {
        graph.removeSeries("Product Price");
        graph.addSeries("Product Price", days, prices);
        graphPanel.revalidate();
        graphPanel.repaint();
    }
}
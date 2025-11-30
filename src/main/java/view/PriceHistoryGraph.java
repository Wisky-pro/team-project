package view;

import org.knowm.xchart.XChartPanel;
import org.knowm.xchart.XYChartBuilder;
import org.knowm.xchart.XYChart;

import javax.swing.*;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.*;


public class PriceHistoryGraph {
    public static void main(String[] args) {
        List<Date> xData = new ArrayList<>();
        List<Double> yData = new ArrayList<>();

        LocalDate today = LocalDate.now();
        int numDates = 10; //number of dates in the x-axis (numDates >= len(yData))

        //Dummy prices & dates
        Map<LocalDate, Double> priceByDate = new HashMap<>();
        priceByDate.put(LocalDate.of(2025, 11, 6), 1.0);
        priceByDate.put(LocalDate.of(2025, 11, 9), 2.0);
        priceByDate.put(LocalDate.of(2025, 11, 10), 1.5);
        priceByDate.put(LocalDate.of(2025, 11, 12), 2.0);
        priceByDate.put(LocalDate.of(2025, 11, 14), 3.0);
        priceByDate.put(LocalDate.of(2025, 11, 15), 1.0);

        Double lastPrice = null; //if data has a gap between days, then last known price will be used

        for(int i = numDates - 1; i >= 0; i--) {
            LocalDate day =  today.minusDays(i - 1);
            ZoneId zone = ZoneId.of("UTC");
            Date asDate = Date.from(day.atStartOfDay(zone).toInstant());
            xData.add(asDate);
            Double price = priceByDate.get(day.minusDays(1));
            if(price != null) {
                yData.add(price);
                lastPrice = price;
            }else {
                yData.add(lastPrice);
            }
        }

        XYChart graph = new XYChartBuilder()
                .width(1200)
                .height(500)
                .title("Price History")
                .xAxisTitle("Date")
                .yAxisTitle("Price ($CAD)")
                .build();

        graph.addSeries("Product Price", xData, yData);

        graph.getStyler().setToolTipsEnabled(true);
        graph.getStyler().setDatePattern("MMM d");
        graph.getStyler().setPlotContentSize(0.78);

        JFrame frame = new JFrame();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.add(new XChartPanel<>(graph));
        frame.pack();
        frame.setVisible(true);
    }
}

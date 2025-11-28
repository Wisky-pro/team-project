import app.AppBuilder;

import javax.swing.*;

public class Main {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {

            AppBuilder appBuilder = new AppBuilder();

            appBuilder.addCartUseCase();

            JFrame frame = new JFrame("Price Tracker Test");
            frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

            frame.add(appBuilder.getPriceTrackerView());
            frame.pack();
            frame.setLocationRelativeTo(null);
            frame.setVisible(true);
        });
    }
}

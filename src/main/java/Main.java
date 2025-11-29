import app.AppBuilder;

import javax.swing.*;

public class Main {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {

            AppBuilder appBuilder = new AppBuilder();
            appBuilder.addDashboardView();

            JFrame frame = new JFrame("Price Tracker Dashboard");
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

            frame.setContentPane(appBuilder.getDashboardView());
            frame.pack();
            frame.setLocationRelativeTo(null);
            frame.setVisible(true);
        });
    }
}

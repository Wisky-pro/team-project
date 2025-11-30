import app.AppBuilder;

import javax.swing.*;

public class Main {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {

            // ------------------- Initialize App -------------------
            AppBuilder appBuilder = new AppBuilder();

            // Setup use cases
            appBuilder.addSignupUseCase();
            appBuilder.addLoginUseCase();
            appBuilder.addCartUseCase(); // includes Dashboard + Account Info wiring

            // ------------------- JFrame setup -------------------
            JFrame frame = new JFrame("Price Tracker");
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

            // Add main panel from ViewManager (handles all views)
            frame.add(appBuilder.getViewManager().getPanel());

            // ------------------- Start with login -------------------
            appBuilder.getViewManagerModel().setActiveView("login");

            // ------------------- Show JFrame -------------------
            frame.pack();
            frame.setLocationRelativeTo(null);
            frame.setVisible(true);
        });
    }
}

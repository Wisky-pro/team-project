import app.AppBuilder;

import javax.swing.*;

public class Main {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {

            AppBuilder appBuilder = new AppBuilder();

            // Setup use cases
            appBuilder.addSignupUseCase();
            appBuilder.addLoginUseCase();
            appBuilder.addCartUseCase();
            appBuilder.addRecommendationUseCase();

            // Create JFrame
            JFrame frame = new JFrame("Price Tracker");
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

            // Add the main panel from ViewManager
            frame.add(appBuilder.getViewManager().getPanel());

            // Show login view at startup
            appBuilder.getViewManagerModel().setActiveView("login");

            frame.pack();
            frame.setLocationRelativeTo(null);
            frame.setVisible(true);
        });
    }
}

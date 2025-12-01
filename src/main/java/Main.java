import app.AppBuilder;

import javax.swing.*;

public class Main {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            AppBuilder appBuilder = new AppBuilder();

            appBuilder.addSignupUseCase()
                      .addLoginUseCase()
                      .addCartUseCase()
                    .addRecommendationUseCase();

            JFrame frame = new JFrame("Price Tracker");
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.add(appBuilder.getViewManager().getPanel());

            // start at login view
            appBuilder.getViewManagerModel().setActiveView("login");
            appBuilder.getViewManagerModel().firePropertyChanged();

            frame.pack();
            frame.setLocationRelativeTo(null);
            frame.setVisible(true);
        });
    }
}

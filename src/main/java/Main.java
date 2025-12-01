import app.AppBuilder;
import javax.swing.*;

public class Main {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            AppBuilder appBuilder = new AppBuilder();

            appBuilder.addSignupUseCase();
            appBuilder.addLoginUseCase();
            appBuilder.addCartUseCase();
            appBuilder.addRecommendationUseCase();

            JFrame frame = new JFrame("Price Tracker");
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.add(appBuilder.getViewManager().getPanel());

            appBuilder.getViewManagerModel().setActiveView("login");
            appBuilder.getViewManagerModel().firePropertyChanged();

            frame.pack();
            frame.setLocationRelativeTo(null);
            frame.setVisible(true);
        });
    }
}

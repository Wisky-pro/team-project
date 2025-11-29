import app.AppBuilder;
import interface_adapter.ViewManagerModel;
import view.LoginView;
import view.SignupView;
import view.PriceTrackerView;
import view.DashboardViewForTest;
import view.ViewManager;

import javax.swing.*;
import java.awt.*;

public class Main {

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {

            AppBuilder app = new AppBuilder();
            app.addLoginUseCase()
                    .addSignupUseCase()
                    .addCartUseCase()
                    .addRecommendationUseCase();

            LoginView loginView = app.getLoginView();
            SignupView signupView = app.getSignupView();
            PriceTrackerView priceTrackerView = app.getPriceTrackerView();
            DashboardViewForTest recommendationView = app.getRecommendationView();
            ViewManagerModel viewManagerModel = app.getViewManagerModel();

            loginView.setSwitchToSignupCallback(() -> {
                viewManagerModel.setActiveView("signup");
                viewManagerModel.firePropertyChanged();
            });

            signupView.setSwitchToLoginCallback(() -> {
                viewManagerModel.setActiveView("login");
                viewManagerModel.firePropertyChanged();
            });

            priceTrackerView.setSwitchToRecommendationCallback(() -> {
                viewManagerModel.setActiveView("recommendation");
                viewManagerModel.firePropertyChanged();
            });

            JFrame frame = new JFrame("Price Tracker");
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

            JPanel screens = new JPanel(new CardLayout());
            screens.add(loginView, "login");
            screens.add(signupView, "signup");
            screens.add(priceTrackerView, "priceTracker");
            screens.add(recommendationView, "recommendation");

            new ViewManager(screens, viewManagerModel);

            frame.setContentPane(screens);
            frame.pack();
            frame.setLocationRelativeTo(null);
            frame.setVisible(true);

            viewManagerModel.setActiveView("login");
            viewManagerModel.firePropertyChanged();
        });
    }
}

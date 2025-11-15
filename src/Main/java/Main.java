import app.AppBuilder;
import interface_adapter.ViewManagerModel;
import view.DashboardView;
import view.LoginView;
import view.SignupView;
import view.ViewManager;

import javax.swing.*;
import java.awt.*;

public class Main {

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {

            AppBuilder app = new AppBuilder();
            app.addLoginUseCase()
               .addSignupUseCase()
               .addDashboardView();

            LoginView loginView = app.getLoginView();
            SignupView signupView = app.getSignupView();
            DashboardView dashboardView = app.getDashboardView();
            ViewManagerModel viewManagerModel = app.getViewManagerModel();

            loginView.setSwitchToSignupCallback(() -> {
                viewManagerModel.setActiveView("signup");
                viewManagerModel.firePropertyChanged();
            });

            signupView.setSwitchToLoginCallback(() -> {
                viewManagerModel.setActiveView("login");
                viewManagerModel.firePropertyChanged();
            });



            JFrame frame = new JFrame("Price Tracker");
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

            JPanel screens = new JPanel(new CardLayout());
            screens.add(loginView, "login");
            screens.add(signupView, "signup");
            screens.add(dashboardView, "dashboard");

            // --- The ViewManager listens to ViewManagerModel and switches screens ---
            new ViewManager(screens, viewManagerModel);

            frame.add(screens);
            frame.pack();
            frame.setLocationRelativeTo(null);
            frame.setVisible(true);

            // At start show login
            viewManagerModel.setActiveView("login");
            viewManagerModel.firePropertyChanged();
        });
    }
}

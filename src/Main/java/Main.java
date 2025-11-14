import app.AppBuilder;
import view.LoginView;
import view.SignupView;

import javax.swing.*;
import java.awt.*;

public class Main {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {

            // Build the app
            AppBuilder appBuilder = new AppBuilder();
            appBuilder.addLoginUseCase();
            appBuilder.addSignupUseCase();

            LoginView loginView = appBuilder.getLoginView();
            SignupView signupView = appBuilder.getSignupView();

            // Main frame
            JFrame frame = new JFrame("Price tracker");
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

            // CardLayout panel
            JPanel cardPanel = new JPanel(new CardLayout());
            cardPanel.add(loginView, "login");
            cardPanel.add(signupView, "signup");

            frame.add(cardPanel);
            frame.pack();
            frame.setLocationRelativeTo(null);
            frame.setVisible(true);

            // Switch between login and signup when buttons are clicked
            // Here we assume LoginView and SignupView have methods to set navigation callbacks
            loginView.setSwitchToSignupCallback(() -> {
                CardLayout cl = (CardLayout) cardPanel.getLayout();
                cl.show(cardPanel, "signup");
            });

            signupView.setSwitchToLoginCallback(() -> {
                CardLayout cl = (CardLayout) cardPanel.getLayout();
                cl.show(cardPanel, "login");
            });
        });
    }
}

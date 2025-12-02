package view;

import interface_adapter.LogIn.LogInController;
import interface_adapter.LogIn.LogInViewModel;

import javax.swing.*;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

public class LoginView extends JPanel implements PropertyChangeListener {

    private final JTextField usernameField = new JTextField(15);
    private final JPasswordField passwordField = new JPasswordField(15);
    private final JButton loginButton = new JButton("Log In");
    private final JButton signupButton = new JButton("Sign Up");
    private final JLabel messageLabel = new JLabel(" ");

    private final LogInController controller;
    private final LogInViewModel viewModel;
    private Runnable switchToSignupCallback;

    public LoginView(LogInController controller, LogInViewModel viewModel) {
        this.controller = controller;
        this.viewModel = viewModel;
        this.viewModel.addPropertyChangeListener(this);

        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));

        add(new JLabel("Username:"));
        add(usernameField);
        add(Box.createVerticalStrut(10));

        add(new JLabel("Password:"));
        add(passwordField);
        add(Box.createVerticalStrut(15));

        add(loginButton);
        add(signupButton);
        add(Box.createVerticalStrut(15));
        add(messageLabel);

        loginButton.addActionListener(e -> controller.login(
                usernameField.getText(),
                new String(passwordField.getPassword())
        ));

        signupButton.addActionListener(e -> {
            if (switchToSignupCallback != null) {
                switchToSignupCallback.run();
            }
        });
    }

    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        switch (evt.getPropertyName()) {
            case "message":
                messageLabel.setText(viewModel.getMessage());
                break;
            case "loggedIn":
                break;
            default:
                System.out.println("Unhandled property change: " + evt.getPropertyName());
                break;
        }
    }

    public void setSwitchToSignupCallback(Runnable callback) {
        this.switchToSignupCallback = callback;
    }

    public void clearPassword() {
        passwordField.setText("");
    }

    @Override
    public void setVisible(boolean aFlag) {
        super.setVisible(aFlag);
        if (aFlag) {
            passwordField.setText("");
        }
    }
}

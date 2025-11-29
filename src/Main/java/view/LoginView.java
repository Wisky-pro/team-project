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
    private final JButton signupButton = new JButton("Sign Up"); // new button
    private final JLabel messageLabel = new JLabel(" ");

    private final LogInController controller;
    private final LogInViewModel viewModel;

    // Callback to switch to signup view
    private Runnable switchToSignupCallback;

    public LoginView(LogInController controller, LogInViewModel viewModel) {
        this.controller = controller;
        this.viewModel = viewModel;

        // Register to listen for changes in the view model
        this.viewModel.addPropertyChangeListener(this);

        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));

        add(new JLabel("Username:"));
        add(usernameField);
        add(Box.createVerticalStrut(10));

        add(new JLabel("Password:"));
        add(passwordField);
        add(Box.createVerticalStrut(15));

        add(loginButton);
        add(signupButton); // add signup button
        add(Box.createVerticalStrut(15));
        add(messageLabel);

        // Login button action
        loginButton.addActionListener(e -> controller.login(
                usernameField.getText(),
                new String(passwordField.getPassword())
        ));

        // Sign up button action
        signupButton.addActionListener(e -> {
            if (switchToSignupCallback != null) {
                switchToSignupCallback.run();
            }
        });
    }

    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        messageLabel.setText(viewModel.getMessage());
    }

    /**
     * Set the callback to switch to the signup view.
     * This keeps the view decoupled from the main frame.
     */
    public void setSwitchToSignupCallback(Runnable callback) {
        this.switchToSignupCallback = callback;
    }
}

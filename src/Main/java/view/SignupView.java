package view;

import interface_adapter.Signup.SignupController;
import interface_adapter.Signup.SignupViewModel;

import javax.swing.*;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

public class SignupView extends JPanel implements PropertyChangeListener {

    private final JTextField usernameField = new JTextField(15);
    private final JPasswordField passwordField = new JPasswordField(15);
    private final JButton signupButton = new JButton("Sign Up");
    private final JButton backButton = new JButton("Back to Login"); // new button

    private final JLabel messageLabel = new JLabel(" ");

    private final SignupController controller;
    private final SignupViewModel viewModel;

    private Runnable switchToLoginCallback; // callback

    public SignupView(SignupController controller, SignupViewModel viewModel) {
        this.controller = controller;
        this.viewModel = viewModel;
        this.viewModel.addPropertyChangeListener(this);

        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));

        add(new JLabel("Username:"));
        add(usernameField);
        add(new JLabel("Password:"));
        add(passwordField);
        add(signupButton);
        add(backButton); // add back button
        add(messageLabel);

        signupButton.addActionListener(e ->
                controller.signup(usernameField.getText(), new String(passwordField.getPassword()))
        );

        backButton.addActionListener(e -> {
            if (switchToLoginCallback != null) {
                switchToLoginCallback.run();
            }
        });
    }

    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        messageLabel.setText(viewModel.getMessage());
    }

    public void setSwitchToLoginCallback(Runnable callback) {
        this.switchToLoginCallback = callback;
    }
}

package view;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;

public class LoggedInView extends JPanel {
    private final JLabel usernameLabel = new JLabel("Username:");
    private final JLabel passwordLabel = new JLabel("Password:");

    private final JTextField usernameField = new JTextField(15);
    private final JPasswordField passwordField = new JPasswordField(15);

    private final JButton loginButton = new JButton("Log In");

    public LoggedInView() {
        this.setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        gbc.gridx = 0; gbc.gridy = 0;
        this.add(usernameLabel, gbc);

        gbc.gridx = 1; gbc.gridy = 0;
        this.add(usernameField, gbc);

        gbc.gridx = 0; gbc.gridy = 1;
        this.add(passwordLabel, gbc);

        gbc.gridx = 1; gbc.gridy = 1;
        this.add(passwordField, gbc);

        gbc.gridx = 1; gbc.gridy = 2;
        this.add(loginButton, gbc);
    }

    // --- Public methods for controller access ---

    public String getUsername() {
        return usernameField.getText();
    }

    public String getPassword() {
        return new String(passwordField.getPassword());
    }

    public void addLoginListener(ActionListener listener) {
        loginButton.addActionListener(listener);
    }

    public void showError(String message) {
        JOptionPane.showMessageDialog(this, message, "Login Error", JOptionPane.ERROR_MESSAGE);
    }
}

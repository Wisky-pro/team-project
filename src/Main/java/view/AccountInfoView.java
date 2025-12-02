package view;

import entity.User;
import entity.UserSession;

import javax.swing.*;

public class AccountInfoView extends JPanel {

    private final JLabel usernameLabel = new JLabel();

    public AccountInfoView() {
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        add(new JLabel("Account Info"));
        add(usernameLabel);
    }

    public void refresh() {
        User user = UserSession.getCurrentUser();
        if (user != null) {
            usernameLabel.setText("Username: " + user.getUsername());
        } else {
            usernameLabel.setText("Username: N/A");
        }
    }
}

package view;

import interface_adapter.Dashboard.DashboardViewModel;
import interface_adapter.Logout.LogoutController;

import javax.swing.*;
import java.awt.*;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

public class DashboardView extends JPanel implements PropertyChangeListener {

    private final DashboardViewModel viewModel;
    private LogoutController logoutController;

    private final JLabel messageLabel = new JLabel(" ");

    public DashboardView(DashboardViewModel viewModel) {
        this.viewModel = viewModel;
        viewModel.addPropertyChangeListener(this);

        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));

        add(Box.createVerticalStrut(15));

        JPanel buttons = new JPanel();

        setLayout(new BorderLayout());

JButton logoutButton = new JButton("Log Out");
logoutButton.addActionListener(e -> {
    if (logoutController != null) {
        logoutController.logout();
    }
});

JPanel bottomRight = new JPanel(new FlowLayout(FlowLayout.RIGHT));
bottomRight.add(logoutButton);

add(bottomRight, BorderLayout.NORTH);


        add(buttons);

        add(Box.createVerticalStrut(10));
        add(messageLabel);
    }

    // Setter for controller
    public void setLogoutController(LogoutController controller) {
        this.logoutController = controller;
    }

    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        messageLabel.setText(viewModel.getMessage());
    }
}

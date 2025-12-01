package view;

import interface_adapter.Dashboard.DashboardViewModel;

import javax.swing.*;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

public class DashboardView extends JPanel implements PropertyChangeListener {

    private final DashboardViewModel viewModel;

    private final JButton accountButton = new JButton("Account Info");

    private final JLabel messageLabel = new JLabel(" ");

    public DashboardView(DashboardViewModel viewModel) {
        this.viewModel = viewModel;
        viewModel.addPropertyChangeListener(this);

        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));

        add(Box.createVerticalStrut(15));

        JPanel buttons = new JPanel();
        buttons.add(accountButton);
        add(buttons);

        add(Box.createVerticalStrut(10));
        add(messageLabel);
    }

    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        messageLabel.setText(viewModel.getMessage());
    }

    public JButton getAccountButton() {
        return accountButton;
    }
}

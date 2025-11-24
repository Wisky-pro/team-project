package view;

import interface_adapter.Dashboard.DashboardViewModel;

import javax.swing.*;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

public class DashboardView extends JPanel implements PropertyChangeListener {

    private final DashboardViewModel viewModel;

    private final JTextField urlField = new JTextField(25);
    private final JButton addButton = new JButton("Track Price");
    private final JButton cartButton = new JButton("View Cart");
    private final JButton accountButton = new JButton("Account Info");

    private final JLabel messageLabel = new JLabel(" ");

    public DashboardView(DashboardViewModel viewModel) {
        this.viewModel = viewModel;
        viewModel.addPropertyChangeListener(this);

        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));

        add(new JLabel("Enter product URL:"));
        add(urlField);

        add(addButton);
        add(Box.createVerticalStrut(10));

        JPanel buttons = new JPanel();
        buttons.add(cartButton);
        buttons.add(accountButton);
        add(buttons);

        add(Box.createVerticalStrut(10));
        add(messageLabel);

    }

    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        messageLabel.setText(viewModel.getMessage());
    }
}

package view;

import interface_adapter.AddToCart.AddToCartController;
import interface_adapter.Cart.CartViewModel;
import interface_adapter.Dashboard.DashboardViewModel;
import interface_adapter.RemoveFromCart.RemoveFromCartController;
import use_case.Cart.CartDataAccessInterface;

import javax.swing.*;
import java.awt.*;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

public class DashboardView extends JPanel implements PropertyChangeListener {

    private final DashboardViewModel viewModel;

    private final JTextField urlField = new JTextField(25);
    private final JTextField quantityField = new JTextField("1", 5);

    private final JButton addButton = new JButton("Add to Cart");
    private final JButton viewCartButton = new JButton("View Cart");
    private final JButton accountButton = new JButton("Account Info");

    private final JLabel messageLabel = new JLabel(" ");

    private final AddToCartController addToCartController;
    private final RemoveFromCartController removeFromCartController;
    private final CartDataAccessInterface cartDataAccess;
    private final CartViewModel cartViewModel;
    private final String username;

    public DashboardView(DashboardViewModel viewModel,
                         AddToCartController addToCartController,
                         RemoveFromCartController removeFromCartController,
                         CartDataAccessInterface cartDataAccess,
                         CartViewModel cartViewModel,
                         String username) {

        this.viewModel = viewModel;
        this.addToCartController = addToCartController;
        this.removeFromCartController = removeFromCartController;
        this.cartDataAccess = cartDataAccess;
        this.cartViewModel = cartViewModel;
        this.username = username;

        this.cartViewModel.addPropertyChangeListener(this);
        this.viewModel.addPropertyChangeListener(this);

        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));

        add(new JLabel("Enter product URL:"));
        add(urlField);

        JPanel qtyPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        qtyPanel.add(new JLabel("Quantity:"));
        qtyPanel.add(quantityField);
        add(qtyPanel);

        add(addButton);
        add(Box.createVerticalStrut(10));

        JPanel buttons = new JPanel();
        buttons.add(viewCartButton);
        buttons.add(accountButton);
        add(buttons);

        add(Box.createVerticalStrut(10));

        JPanel messagePanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        Dimension msgSize = new Dimension(400, 20);
        messageLabel.setPreferredSize(msgSize);
        messageLabel.setMinimumSize(msgSize);
        messageLabel.setMaximumSize(msgSize);
        messagePanel.add(messageLabel);
        add(messagePanel);

        addButton.addActionListener(e -> {
            String url = urlField.getText().trim();
            String quantityText = quantityField.getText().trim();

            int quantity;
            try {
                quantity = Integer.parseInt(quantityText);
                if (quantity <= 0) {
                    messageLabel.setText("Quantity must be a positive integer.");
                    urlField.setText("");
                    quantityField.setText("1");
                    urlField.requestFocusInWindow();
                    return;
                }
            } catch (NumberFormatException ex) {
                messageLabel.setText("Quantity must be a positive integer.");
                urlField.setText("");
                quantityField.setText("1");
                urlField.requestFocusInWindow();
                return;
            }

            if (url.isEmpty()) {
                messageLabel.setText("Please enter a URL.");
                return;
            }

            addToCartController.execute(username, url, quantity);

            urlField.setText("");
            quantityField.setText("1");
            urlField.requestFocusInWindow();
        });

        viewCartButton.addActionListener(e -> {
            CartWindow window =
                    new CartWindow(cartDataAccess, removeFromCartController, cartViewModel, username);
            window.setVisible(true);
        });

        accountButton.addActionListener(e -> {
            JOptionPane.showMessageDialog(
                    this,
                    "Account screen not implemented yet.",
                    "Account Info",
                    JOptionPane.INFORMATION_MESSAGE
            );
        });
    }

    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        // Just mirror cart messages for now (like PriceTrackerView)
        messageLabel.setText(cartViewModel.getMessage());
    }
}

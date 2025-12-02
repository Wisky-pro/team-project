package view;

import interface_adapter.AddToCart.AddToCartController;
import interface_adapter.Cart.CartViewModel;
import interface_adapter.PriceHistory.PriceHistoryController;
import interface_adapter.PriceHistory.PriceHistoryViewModel;
import interface_adapter.RemoveFromCart.RemoveFromCartController;
import use_case.Cart.CartDataAccessInterface;
import interface_adapter.LogIn.LogInViewModel;  // import

import javax.swing.*;
import java.awt.*;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

public class PriceTrackerView extends JPanel implements PropertyChangeListener {

    private final JTextField urlField = new JTextField(30);
    private final JTextField quantityField = new JTextField("1", 5);
    private final JButton addButton = new JButton("Add to Cart");
    private final JButton viewCartButton = new JButton("View Cart");
    private final JLabel messageLabel = new JLabel(" ");
    private final JLabel totalLabel = new JLabel("Total: $0.00");

    private final AddToCartController addToCartController;
    private final RemoveFromCartController removeFromCartController;
    private final CartViewModel cartViewModel;
    private final CartDataAccessInterface cartDataAccess;
    private final LogInViewModel loginVM;  // use view-model instead of fixed username
    private final PriceHistoryViewModel historyViewModel;
    private final PriceHistoryController priceHistoryController;


    public PriceTrackerView(
            AddToCartController addToCartController,
            RemoveFromCartController removeFromCartController,
            CartViewModel cartViewModel,
            CartDataAccessInterface cartDataAccess,
            LogInViewModel loginVM,
            PriceHistoryViewModel historyViewModel,
            PriceHistoryController priceHistoryController// <--- changed
    ) {
        this.addToCartController = addToCartController;
        this.removeFromCartController = removeFromCartController;
        this.cartViewModel = cartViewModel;
        this.cartDataAccess = cartDataAccess;
        this.loginVM = loginVM;
        this.historyViewModel = historyViewModel;
        this.priceHistoryController = priceHistoryController;

        this.cartViewModel.addPropertyChangeListener(this);

        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));

        JPanel urlPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        urlPanel.add(new JLabel("Item URL:"));
        urlPanel.add(urlField);
        urlPanel.add(new JLabel("Quantity:"));
        urlPanel.add(quantityField);
        urlPanel.add(addButton);
        urlPanel.add(viewCartButton);

        JPanel infoPanel = new JPanel(new GridLayout(2, 1));
        infoPanel.add(messageLabel);
        infoPanel.add(totalLabel);

        add(urlPanel);
        add(infoPanel);

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

            String username = loginVM.getUsername();  // get current username
            addToCartController.execute(username, url, quantity);

            urlField.setText("");
            quantityField.setText("1");
            urlField.requestFocusInWindow();
        });

        viewCartButton.addActionListener(e -> {
            String username = loginVM.getUsername();  // use current username
            CartWindow window = new CartWindow(cartDataAccess, removeFromCartController, cartViewModel, username,
                    historyViewModel, priceHistoryController);
            window.setVisible(true);
        });
    }

    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        messageLabel.setText(cartViewModel.getMessage());
        totalLabel.setText(String.format("Total: $%.2f", cartViewModel.getTotal()));
    }
}


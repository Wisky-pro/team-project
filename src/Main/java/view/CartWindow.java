package view;

import entity.Cart;
import entity.CartItem;
import interface_adapter.Cart.CartViewModel;
import interface_adapter.PriceHistory.PriceHistoryController;
import interface_adapter.PriceHistory.PriceHistoryViewModel;
import interface_adapter.RemoveFromCart.RemoveFromCartController;
import use_case.Cart.CartDataAccessInterface;

import javax.swing.*;
import java.awt.*;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

public class CartWindow extends JFrame implements PropertyChangeListener {

    private final CartDataAccessInterface cartDataAccess;
    private final RemoveFromCartController removeController;
    private final CartViewModel cartViewModel;
    private final String username;
    private final PriceHistoryViewModel historyViewModel;
    private final PriceHistoryController historyController;

    private final JPanel itemsPanel = new JPanel();
    private final JTextField quantityField = new JTextField("1", 5);
    private final JLabel selectedLabel = new JLabel("Selected: none");
    private final JLabel messageLabel = new JLabel(" ");
    private final JLabel totalLabel = new JLabel("Total: $0.00");

    private String selectedProductUrl;

    public CartWindow(CartDataAccessInterface cartDataAccess,
                      RemoveFromCartController removeController,
                      CartViewModel cartViewModel,
                      String username,
                      PriceHistoryViewModel historyViewModel,
                      PriceHistoryController historyController) {

        this.cartDataAccess = cartDataAccess;
        this.removeController = removeController;
        this.cartViewModel = cartViewModel;
        this.username = username;
        this.historyViewModel = historyViewModel;
        this.historyController = historyController;


        cartViewModel.addPropertyChangeListener(this);

        setTitle("Cart");
        setLayout(new BorderLayout());

        itemsPanel.setLayout(new BoxLayout(itemsPanel, BoxLayout.Y_AXIS));
        JScrollPane scrollPane = new JScrollPane(itemsPanel);

        JPanel bottomPanel = new JPanel();
        bottomPanel.setLayout(new GridLayout(3, 1));

        JPanel row1 = new JPanel(new FlowLayout(FlowLayout.LEFT));
        row1.add(selectedLabel);

        JPanel row2 = new JPanel(new FlowLayout(FlowLayout.LEFT));
        row2.add(new JLabel("Quantity to remove:"));
        row2.add(quantityField);
        JButton removeButton = new JButton("Remove");
        row2.add(removeButton);

        JPanel row3 = new JPanel(new GridLayout(1, 2));
        row3.add(messageLabel);
        row3.add(totalLabel);

        bottomPanel.add(row1);
        bottomPanel.add(row2);
        bottomPanel.add(row3);

        add(scrollPane, BorderLayout.CENTER);
        add(bottomPanel, BorderLayout.SOUTH);

        removeButton.addActionListener(e -> handleRemove());

        refreshItems();

        pack();
        setLocationRelativeTo(null);
    }

    private void refreshItems() {
        itemsPanel.removeAll();
        Cart cart = cartDataAccess.getCart(username);

        if (cart == null || cart.isEmpty()) {
            itemsPanel.add(new JLabel("Cart is empty."));
            totalLabel.setText("Total: $0.00");
        } else {
            for (CartItem item : cart.getItems()) {
                String url_target = item.getProductUrl();

                String text = item.getName() + " | $" + item.getPrice()
                        + " x " + item.getQuantity() + " | " + cart.getTargetPrice(url_target);
                JButton button = new JButton(text);
                String url = item.getProductUrl();
                button.addActionListener(e -> {
                    selectedProductUrl = url;
                    selectedLabel.setText("Selected: " + item.getName());
                });

                JButton priceHistoryButton = new JButton("Show price history");

                priceHistoryButton.addActionListener(e -> {
                    historyController.viewPriceHistory(url);
                    PriceHistoryGraphView graph = new PriceHistoryGraphView(historyViewModel);
                    SwingUtilities.invokeLater(() -> {
                        JFrame frame = new JFrame("Price History Test");
                        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                        frame.setContentPane(graph);
                        frame.pack();
                        frame.setVisible(true);
                    });
                });

                JPanel productRow = new JPanel();
                productRow.setLayout(new BoxLayout(productRow, BoxLayout.X_AXIS));

                productRow.add(button);
                productRow.add(priceHistoryButton);

                itemsPanel.add(productRow);

                button.addActionListener(e -> {
                    selectedProductUrl = url;
                    selectedLabel.setText("Selected: " + item.getName());
                });



                itemsPanel.add(button);
            }
            totalLabel.setText(String.format("Total: $%.2f", cart.getTotal()));
        }

        itemsPanel.revalidate();
        itemsPanel.repaint();
    }

    private void handleRemove() {
        if (selectedProductUrl == null) {
            messageLabel.setText("Please select an item first.");
            return;
        }

        String text = quantityField.getText().trim();
        int quantity;
        try {
            quantity = Integer.parseInt(text);
            if (quantity <= 0) {
                messageLabel.setText("Quantity must be a positive integer.");
                return;
            }
        } catch (NumberFormatException ex) {
            messageLabel.setText("Quantity must be a positive integer.");
            return;
        }

        removeController.execute(username, selectedProductUrl, quantity);
    }

    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        messageLabel.setText(cartViewModel.getMessage());
        totalLabel.setText(String.format("Total: $%.2f", cartViewModel.getTotal()));
        refreshItems();
    }
}

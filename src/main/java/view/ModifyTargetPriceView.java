package view;

import entity.CartItem;
import interface_adapter.Cart.CartViewModel;
import interface_adapter.ModifyTargetPrice.ModifyTargetPriceController;
import interface_adapter.ModifyTargetPrice.ModifyTargetPriceViewModel;
import interface_adapter.RemoveFromCart.RemoveFromCartController;
import use_case.Cart.CartDataAccessInterface;
import use_case.ModifyTargetPrice.ModifyTargetPriceDataAccessInterface;
import interface_adapter.*;

import javax.swing.*;
import java.awt.*;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;

public class ModifyTargetPriceView extends JFrame implements PropertyChangeListener {
    private final ModifyTargetPriceDataAccessInterface dataAccessInterface;
    private final ModifyTargetPriceController controller;
    private final ModifyTargetPriceViewModel viewModel;

    private CartItem item;

    private final JPanel ModifyTargetPricePanel = new JPanel();
    private final JTextField updatedTargetPriceField = new JTextField(10);
    private final JLabel message = new JLabel(" ");
    
    public ModifyTargetPriceView(ModifyTargetPriceDataAccessInterface dataAccessInterface, 
                                ModifyTargetPriceController controller,
                                ModifyTargetPriceViewModel model
                                ) {
    
            this.dataAccessInterface = dataAccessInterface;
            this.controller = controller;
            this.viewModel = model;

            viewModel.addPropertyChangeListener(this);

            setTitle("Cart");
            setLayout(new BorderLayout());

            ModifyTargetPricePanel.setLayout(new BoxLayout(ModifyTargetPricePanel, BoxLayout.Y_AXIS));

            // int currentTargetPrice = dataAccessInterface.getCurrentTargetPrice(item);

            JButton updateButton = new JButton("Update price");

            updateButton.addActionListener(e -> updateTargetPrice());

            pack();
    }

    private void updateTargetPrice() {
        // Do the required stuff in here
        int updatedPrice = 0;

        String input = updatedTargetPriceField.getText().trim();
        try {
            updatedPrice = Integer.parseInt(input);

            if (updatedPrice <= 0) {
                message.setText("Your updated price must be negative. ");
                return;
            }
        }

        catch (NumberFormatException formatError) {
            message.setText("There is a error with the price you tried to input.");
            return;
        }

        controller.execute(item, updatedPrice);
    }

    @Override
    public void propertyChange(PropertyChangeEvent event) {
        message.setText(viewModel.getMessage());

        // Maybe clear out the text field also?
    }
}

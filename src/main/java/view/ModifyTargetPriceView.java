package view;

import interface_adapter.ModifyTargetPrice.ModifyTargetPriceController;
import interface_adapter.ModifyTargetPrice.ModifyTargetPriceViewModel;
import use_case.ModifyTargetPrice.ModifyTargetPriceDataAccessInterface;
import javax.swing.*;
import java.awt.*;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

public class ModifyTargetPriceView extends JFrame implements PropertyChangeListener {
    private final ModifyTargetPriceController controller;
    private final ModifyTargetPriceViewModel viewModel;
    private final String username;
    private final String productUrl;

    private final JPanel ModifyTargetPricePanel = new JPanel();
    private final JTextField updatedTargetPriceField = new JTextField(10);
    private final JLabel message = new JLabel(" ");
    
    public ModifyTargetPriceView(ModifyTargetPriceDataAccessInterface dataAccessInterface, 
                                ModifyTargetPriceController controller,
                                ModifyTargetPriceViewModel model,
                                String username,
                                String productUrl
                                ) {
    
            this.controller = controller;
            this.viewModel = model;
            this.username = username;
            this.productUrl = productUrl;

            viewModel.addPropertyChangeListener(this);

            setTitle("Update target price");
            setLayout(new BorderLayout());

            ModifyTargetPricePanel.setLayout(new BoxLayout(ModifyTargetPricePanel, BoxLayout.Y_AXIS));

            JButton updateButton = new JButton("Update price");
            updateButton.addActionListener(e -> updateTargetPrice());

            pack();
    }

    private void updateTargetPrice() {
        int updatedPrice = 0;

        String input = updatedTargetPriceField.getText().trim();
        try {
            updatedPrice = Integer.parseInt(input);

            if (updatedPrice <= 0) {
                message.setText("Your updated price must be positive. ");
                return;
            }
        }

        catch (NumberFormatException formatError) {
            message.setText("There is a error with the price you tried to input.");
            return;
        }

        controller.execute(username, productUrl, updatedPrice);
    }

    @Override
    public void propertyChange(PropertyChangeEvent event) {
        message.setText(viewModel.getMessage());

        updatedTargetPriceField.setText("");
    }
}

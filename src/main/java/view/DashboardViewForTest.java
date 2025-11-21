package view;

import interface_adapter.Dashboard.DashboardViewModel;
import interface_adapter.Recommendation.PurchaseRecommendationController;

import javax.swing.*;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

public class DashboardViewForTest extends JPanel implements PropertyChangeListener {

    private final DashboardViewModel viewModel;
    private final PurchaseRecommendationController controller;

    private final JTextField nameField = new JTextField(20);
    private final JButton button = new JButton("Get Recommendation");
    private final JTextArea resultArea = new JTextArea(5, 30);

    public DashboardViewForTest(DashboardViewModel viewModel,
                                PurchaseRecommendationController controller) {
        this.viewModel = viewModel;
        this.controller = controller;

        this.viewModel.addPropertyChangeListener(this);

        this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        this.add(new JLabel("Commodity name:"));
        this.add(nameField);
        this.add(button);
        this.add(new JScrollPane(resultArea));

        button.addActionListener(e -> {
            String name = nameField.getText();
            controller.getRecommendation(name);
        });
    }

    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        if ("dashboardMesssage".equals(evt.getPropertyName())) {
            resultArea.setText(viewModel.getMessage());
        }
    }
}
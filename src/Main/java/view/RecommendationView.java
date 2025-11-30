package view;

import interface_adapter.Dashboard.DashboardViewModel;
import interface_adapter.Recommendation.PurchaseRecommendationController;

import javax.swing.*;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

public class RecommendationView extends JPanel implements PropertyChangeListener {

    private final transient DashboardViewModel viewModel;
    private transient Runnable switchToPriceTrackerViewCallback;
    private final JTextField nameField = new JTextField(20);
    private final JTextArea resultArea = new JTextArea(5, 30);

    public RecommendationView(DashboardViewModel viewModel,
                              PurchaseRecommendationController controller) {
        this.viewModel = viewModel;

        this.viewModel.addPropertyChangeListener(this);

        this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        this.add(new JLabel("Commodity name:"));
        this.add(nameField);
        JButton button = new JButton("Get Recommendation");
        this.add(button);
        JButton backButton = new JButton("Back to PriceTrackerView");
        this.add(backButton);
        this.add(new JScrollPane(resultArea));

        button.addActionListener(e -> {
            String name = nameField.getText();
            controller.getRecommendation(name);
        });
        backButton.addActionListener(e -> {
            if (switchToPriceTrackerViewCallback != null) {
                switchToPriceTrackerViewCallback.run();
            }
        });
    }

    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        if ("dashboardMesssage".equals(evt.getPropertyName())) {
            resultArea.setText(viewModel.getMessage());
        }
    }
    public void setSwitchToPriceTrackerViewCallback(Runnable callback) {
        this.switchToPriceTrackerViewCallback = callback;
    }
}
package interface_adapter;

import javax.swing.*;
import java.awt.*;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.HashMap;
import java.util.Map;

public class ViewManager implements PropertyChangeListener {

    private final JPanel mainPanel = new JPanel();
    private final CardLayout cardLayout = new CardLayout();
    private final Map<String, JPanel> views = new HashMap<>();

    public ViewManager(ViewManagerModel viewManagerModel) {
        mainPanel.setLayout(cardLayout);
        viewManagerModel.addPropertyChangeListener(this);
    }

    public void addView(JPanel view, String viewName) {
        views.put(viewName, view);
        mainPanel.add(view, viewName);
    }

    public JPanel getPanel() {
        return mainPanel;
    }

    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        String viewName = (String) evt.getNewValue();
        cardLayout.show(mainPanel, viewName);
    }
}

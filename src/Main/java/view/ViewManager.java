package view;

import interface_adapter.ViewManagerModel;

import javax.swing.*;
import java.awt.*;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

public class ViewManager implements PropertyChangeListener {

    private final JPanel cardPanel;
    private final ViewManagerModel viewModel;

    public ViewManager(JPanel cardPanel, ViewManagerModel viewModel) {
        this.cardPanel = cardPanel;
        this.viewModel = viewModel;
        this.viewModel.addPropertyChangeListener(this);
    }

    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        CardLayout cl = (CardLayout) cardPanel.getLayout();
        cl.show(cardPanel, viewModel.getActiveView());
    }
}

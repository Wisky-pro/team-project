package interface_adapter;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;

public class ViewManagerModel {

    private final PropertyChangeSupport support = new PropertyChangeSupport(this);
    private String activeView;

    public String getActiveView() {
        return activeView;
    }

    public void setActiveView(String activeView) {
        this.activeView = activeView;
        firePropertyChanged();
    }

    public void addPropertyChangeListener(PropertyChangeListener listener) {
        support.addPropertyChangeListener(listener);
    }

    public void firePropertyChanged(){
        support.firePropertyChange("view", null, activeView);
    }
}

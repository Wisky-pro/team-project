package interface_adapter.Dashboard;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;

public class DashboardViewModel {

    private final PropertyChangeSupport support = new PropertyChangeSupport(this);
    private String message = "";

    public void setMessage(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }

    public void addPropertyChangeListener(PropertyChangeListener listener) {
        support.addPropertyChangeListener(listener);
    }

    public void firePropertyChanged(){
        support.firePropertyChange("dashboardMesssage", null, message);
    }
}

package interface_adapter.Dashboard;

import view.DashboardView;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;

public class DashboardViewModel {

    private final PropertyChangeSupport support = new PropertyChangeSupport(this);
    private DashboardView dashboardView;
    private String message = " ";

    public void setDashboardView(DashboardView dashboardView) {
        this.dashboardView = dashboardView;
    }

    public DashboardView getDashboardView() {
        return dashboardView;
    }

    public void addPropertyChangeListener(PropertyChangeListener listener) {
        support.addPropertyChangeListener(listener);
    }

    public void setMessage(String message) {
        String old = this.message;
        this.message = message;
        support.firePropertyChange("message", old, message);
    }

    public String getMessage() {
        return message;
    }

    public void firePropertyChanged() {
        support.firePropertyChange("state", null, this);
    }
}

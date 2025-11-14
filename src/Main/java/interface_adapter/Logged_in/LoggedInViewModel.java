package interface_adapter.Logged_in;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;

public class LoggedInViewModel {

    private final PropertyChangeSupport support = new PropertyChangeSupport(this);

    private String username = "";

    public void addPropertyChangeListener(PropertyChangeListener listener) {
        support.addPropertyChangeListener(listener);
    }

    public void setUsername(String username) {
        String old = this.username;
        this.username = username;
        support.firePropertyChange("username", old, username);
    }

    public String getUsername() {
        return username;
    }
}

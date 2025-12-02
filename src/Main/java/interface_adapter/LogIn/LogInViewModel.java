package interface_adapter.LogIn;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;

public class LogInViewModel {

    private String message = "";
    private String username = "";
    private String password = "";
    private boolean loggedIn = false;

    private final PropertyChangeSupport support = new PropertyChangeSupport(this);

    public void addPropertyChangeListener(PropertyChangeListener listener) {
        support.addPropertyChangeListener(listener);
    }

    public void firePropertyChanged() {
        support.firePropertyChange("state", null, this);
    }

    public String getMessage() { return message; }

    public void setMessage(String message) {
        String old = this.message;
        this.message = message;
        support.firePropertyChange("message", old, message);
    }

    public void setUsername(String username) { this.username = username; }
    public String getUsername() { return username; }

    public void setPassword(String password) { this.password = password; }
    public String getPassword() { return password; }

    public boolean isLoggedIn() { return loggedIn; }

    public void setLoggedIn(boolean loggedIn) {
        boolean old = this.loggedIn;
        this.loggedIn = loggedIn;
        support.firePropertyChange("loggedIn", old, loggedIn);
    }

    public void setSuccessMessage(String message) {
        setMessage(message);
        setLoggedIn(true);
    }

    public void setErrorMessage(String error) {
        setMessage(error);
        setLoggedIn(false);
    }

    // ✅ NEW METHOD: reset login UI state
    public void reset() {
        String oldUsername = this.username;
        String oldPassword = this.password;
        String oldMessage = this.message;
        boolean oldLoggedIn = this.loggedIn;

        this.username = "";
        this.password = "";
        this.message = "";
        this.loggedIn = false;

        support.firePropertyChange("username", oldUsername, this.username);
        support.firePropertyChange("password", oldPassword, this.password);
        support.firePropertyChange("message", oldMessage, this.message);
        support.firePropertyChange("loggedIn", oldLoggedIn, this.loggedIn);

        // general UI update event
        firePropertyChanged();
    }
}

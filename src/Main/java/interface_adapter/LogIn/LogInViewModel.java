package interface_adapter.LogIn;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;

public class LogInViewModel {

    private String message = "";
    private boolean loggedIn = false;
    private String password = "";
    private final String viewName = "login";

    private final PropertyChangeSupport support = new PropertyChangeSupport(this);

    public void addPropertyChangeListener(PropertyChangeListener listener) {
        support.addPropertyChangeListener(listener);
    }

    public void firePropertyChanged() {
        support.firePropertyChange("state", null, this);
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        String old = this.message;
        this.message = message;
        support.firePropertyChange("message", old, message);
    }

    public boolean isLoggedIn() {
        return loggedIn;
    }

    public void setLoggedIn(boolean loggedIn) {
        boolean old = this.loggedIn;
        this.loggedIn = loggedIn;
        support.firePropertyChange("loggedIn", old, loggedIn);
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        String old = this.password;
        this.password = password;
        support.firePropertyChange("password", old, password);
    }

    public String getViewName() {
        return viewName;
    }

    public void setSuccessMessage(String s) {
        String oldMessage = this.message;
        boolean oldLoggedIn = this.loggedIn;

        this.message = s;
        this.loggedIn = true;

        support.firePropertyChange("message", oldMessage, s);
        support.firePropertyChange("loggedIn", oldLoggedIn, true);
    }

    public void setErrorMessage(String error) {
        String oldMessage = this.message;
        boolean oldLoggedIn = this.loggedIn;

        this.message = error;
        this.loggedIn = false;

        support.firePropertyChange("message", oldMessage, error);
        support.firePropertyChange("loggedIn", oldLoggedIn, false);
    }

    public void reset() {
        setMessage("");
        setPassword("");
        setLoggedIn(false);
    }
}

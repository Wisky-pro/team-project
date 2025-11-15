package interface_adapter.Signup;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;

public class SignupViewModel {

    private final PropertyChangeSupport pcs = new PropertyChangeSupport(this);
    private String message = " ";


    private void updateMessage(String msg) {
        String old = message;
        message = msg;
        pcs.firePropertyChange("message", old, message);
    }

    public void setSuccessMessage(String msg) {
        updateMessage(msg);
    }

    public void setErrorMessage(String msg) {
        updateMessage(msg);
    }

    public String getMessage() {
        return message;
    }

    public void addPropertyChangeListener(PropertyChangeListener listener) {
        pcs.addPropertyChangeListener(listener);
    }

    public void firePropertyChanged(){
        pcs.firePropertyChange("message", null, this.message);
    }

}

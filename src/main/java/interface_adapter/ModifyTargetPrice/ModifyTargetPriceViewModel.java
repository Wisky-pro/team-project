package interface_adapter.ModifyTargetPrice;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;

public class ModifyTargetPriceViewModel {
    private final PropertyChangeSupport support = new PropertyChangeSupport(this);
    private String message = " ";

    public String getMessage() {
        return message;
    }

    public void setMessage(String newMessage) {
        String original = message;
        this.message = newMessage;
        support.firePropertyChange(original, newMessage, original);
    }

    public void addPropertyChangeListener(PropertyChangeListener listener) {
        support.addPropertyChangeListener(listener);
    }

    public void firePropertyChanged() {
        support.firePropertyChange("state", null, this);
    }
}

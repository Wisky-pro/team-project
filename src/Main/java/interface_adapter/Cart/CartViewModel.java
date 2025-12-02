package interface_adapter.Cart;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;

public class CartViewModel {

    private final PropertyChangeSupport support = new PropertyChangeSupport(this);

    private String message = "";
    private double total = 0.0;

    public void addPropertyChangeListener(PropertyChangeListener listener) {
        support.addPropertyChangeListener(listener);
    }

    public String getMessage() {
        return message;
    }

    public double getTotal() {
        return total;
    }

    public void setMessage(String message) {
        String old = this.message;
        this.message = message;
        support.firePropertyChange("message", old, message);
    }

    public void setTotal(double total) {
        double old = this.total;
        this.total = total;
        support.firePropertyChange("total", old, total);
    }
}

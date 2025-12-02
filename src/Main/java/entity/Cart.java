package entity;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

public class Cart {

    private final Map<String, CartItem> items = new HashMap<>();
    private final Map<String, Integer> targetPrices = new HashMap<>();

    public void addItem(String productUrl, String name, double price, int quantity) {
        if (items.containsKey(productUrl)) {
            CartItem existing = items.get(productUrl);
            existing.increaseQuantity(quantity);
        } else {
            CartItem item = new CartItem(productUrl, name, price, quantity);
            items.put(productUrl, item);
        }
    }

    public void addItem(String productUrl, String name, int price, int quantity, int targetPrice) {
        if (items.containsKey(productUrl)) {
            CartItem existing = items.get(productUrl);
            existing.increaseQuantity(quantity);
        } else {
            CartItem item = new CartItem(productUrl, name, price, quantity);
            items.put(productUrl, item);
        }

        updateItem(productUrl, targetPrice);
    }

    public void updateItem(String productUrl, int targetPrice) {
        targetPrices.put(productUrl, targetPrice);
    }

    public int getTargetPrice(String productUrl) {
        return targetPrices.get(productUrl);
    }

    public void removeItem(String productUrl, int quantity) {
        if (!items.containsKey(productUrl)) { //items doesnt contain a url key
            return;
        }
        CartItem item = items.get(productUrl);
        item.decreaseQuantity(quantity);
        if (item.getQuantity() <= 0) {
            items.remove(productUrl);
        }
    }

    public void removeCompletely(String productUrl) {
        items.remove(productUrl);
    }

    public Collection<CartItem> getItems() {
        return items.values();
    }

    public CartItem getItemByUrl(String productUrl) {
        return items.get(productUrl);
    }

    public double getTotal() {
        double total = 0.0;
        for (CartItem item : items.values()) {
            total += item.getPrice() * item.getQuantity();
        }
        return total;
    }

    public boolean isEmpty() {
        return items.isEmpty();
    }
}

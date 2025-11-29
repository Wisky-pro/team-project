package entity;

public class CartItem {

    private final String productUrl;
    private final String name;
    private final double price;
    private int quantity;

    public CartItem(String productUrl, String name, double price, int quantity) {
        this.productUrl = productUrl;
        this.name = name;
        this.price = price;
        this.quantity = quantity;
    }

    public String getProductUrl() {
        return productUrl;
    }

    public String getName() {
        return name;
    }

    public double getPrice() {
        return price;
    }

    public int getQuantity() {
        return quantity;
    }

    public void increaseQuantity(int amount) {
        this.quantity += amount;
    }

    public void decreaseQuantity(int amount) {
        this.quantity -= amount;
    }
}

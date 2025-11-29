package entity;

public class CartItem {

    private final String productUrl;
    private final String name;
    private final double price;
    private int quantity;
    private int targetPrice;

    public CartItem(String productUrl, String name, double price, int quantity) {
        this.productUrl = productUrl;
        this.name = name;
        this.price = price;
        this.quantity = quantity;
    }

    // alternative constructor for adding target price. We can delete the original one later. 

    public CartItem(String productUrl, String name, double price, int quantity, int targetPrice) {
        this.productUrl = productUrl;
        this.name = name;
        this.price = price;
        this.quantity = quantity;
        this.targetPrice = targetPrice;
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

    public int getTargetPrice() {
        return targetPrice;
    }

    public void increaseQuantity(int amount) {
        this.quantity += amount;
    }

    public void decreaseQuantity(int amount) {
        this.quantity -= amount;
    }

    public void setTargetPrice(int newPrice) {
        targetPrice = newPrice;
    }
}

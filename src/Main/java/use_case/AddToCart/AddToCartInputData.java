package use_case.AddToCart;

public class AddToCartInputData {

    private final String username;
    private final String productUrl;
    private final int quantity;
    private final int targetPrice;

    public AddToCartInputData(String username, String productUrl, int quantity) {
        this.username = username;
        this.productUrl = productUrl;
        this.quantity = quantity;
        this.targetPrice = 0;
    }

    public AddToCartInputData(String username, String productUrl, int quantity, int targetPrice) {
        this.username = username;
        this.productUrl = productUrl;
        this.quantity = quantity;
        this.targetPrice = targetPrice;
    }

    public String getUsername() {
        return username;
    }

    public String getProductUrl() {
        return productUrl;
    }

    public int getQuantity() {
        return quantity;
    }

    public int getTargetPrice() { return targetPrice; }
}

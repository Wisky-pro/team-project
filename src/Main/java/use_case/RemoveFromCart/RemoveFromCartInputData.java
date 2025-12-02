package use_case.RemoveFromCart;

public class RemoveFromCartInputData {

    private final String username;
    private final String productUrl;
    private final int quantity;

    public RemoveFromCartInputData(String username, String productUrl, int quantity) {
        this.username = username;
        this.productUrl = productUrl;
        this.quantity = quantity;
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
}

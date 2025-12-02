package use_case.AddToCart;

public class AddToCartOutputData {

    private final String productName;
    private final double cartTotal;

    public AddToCartOutputData(String productName, double cartTotal) {
        this.productName = productName;
        this.cartTotal = cartTotal;
    }

    public String getProductName() {
        return productName;
    }

    public double getCartTotal() {
        return cartTotal;
    }
}

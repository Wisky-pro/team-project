package use_case.RemoveFromCart;

public class RemoveFromCartOutputData {

    private final String productName;
    private final double total;

    public RemoveFromCartOutputData(String productName, double total) {
        this.productName = productName;
        this.total = total;
    }

    public String getProductName() {
        return productName;
    }

    public double getTotal() {
        return total;
    }
}

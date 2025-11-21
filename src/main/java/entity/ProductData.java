package entity;

public class ProductData {

    private final String productUrl;
    private final String name;
    private final double price;

    public ProductData(String productUrl, String name, double price) {
        this.productUrl = productUrl;
        this.name = name;
        this.price = price;
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
}

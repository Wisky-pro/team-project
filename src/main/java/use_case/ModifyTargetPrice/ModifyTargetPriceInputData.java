package use_case.ModifyTargetPrice;

public class ModifyTargetPriceInputData {
    private final String username;
    private final String productUrl;
    private final int newPrice;
    
    public ModifyTargetPriceInputData(String username, String productUrl, int newPrice) {
        this.username = username;
        this.newPrice = newPrice;
        this.productUrl = productUrl;
    }

    public int getUpdatedPrice() {
        return newPrice;
    }

    public String getUsername() {
        return username;
    }

    public String getProductUrl() {
        return productUrl;
    }
}

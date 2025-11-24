package use_case.ModifyTargetPrice;
import entity.CartItem;

public class ModifyTargetPriceInputData {
    private final CartItem item;  
    private final int newPrice;
    
    public ModifyTargetPriceInputData(CartItem item, int newPrice) {
        this.item = item;
        this.newPrice = newPrice;
    }

    public CartItem getItem() {
        return item;
    }

    public int getPrice() {
        return newPrice;
    }
}

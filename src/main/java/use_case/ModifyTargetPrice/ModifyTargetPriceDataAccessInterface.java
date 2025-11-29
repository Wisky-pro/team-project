package use_case.ModifyTargetPrice;
import entity.Cart;

public interface ModifyTargetPriceDataAccessInterface {
    Cart getCart(String username);
    
    void saveCart(String username, Cart cart);
}

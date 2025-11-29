package use_case.ModifyTargetPrice;

import entity.CartItem;

public interface ModifyTargetPriceDataAccessInterface {
    boolean isValidPrice(int price);

    // maybe this isn't necsessary 
    // int getCurrentTargetPrice(CartItem item);
    void setCurrentTargetPrice(CartItem item, int price);
}

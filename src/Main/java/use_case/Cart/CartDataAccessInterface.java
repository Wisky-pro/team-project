package use_case.Cart;

import entity.Cart;

public interface CartDataAccessInterface {

    Cart getCart(String username);

    void saveCart(String username, Cart cart);
}

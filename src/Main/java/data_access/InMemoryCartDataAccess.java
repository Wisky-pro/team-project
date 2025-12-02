package data_access;

import entity.Cart;
import use_case.Cart.CartDataAccessInterface;

import java.util.HashMap;
import java.util.Map;

public class InMemoryCartDataAccess implements CartDataAccessInterface {

    private final Map<String, Cart> carts = new HashMap<>();

    @Override
    public Cart getCart(String username) {
        if (!carts.containsKey(username)) {
            carts.put(username, new Cart());
        }
        return carts.get(username);
    }

    @Override
    public void saveCart(String username, Cart cart) {
        carts.put(username, cart);
    }
}

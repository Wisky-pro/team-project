package data_access;

import entity.Cart;
import entity.CartItem;
import use_case.Cart.CartDataAccessInterface;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.util.*;

import org.json.JSONArray;
import org.json.JSONObject;

public class FileCartDataAccessObject implements CartDataAccessInterface {

    private final String filename;
    private Map<String, Cart> cartData;

    public FileCartDataAccessObject(String filename) {
        this.filename = filename;
        this.cartData = loadCart();
    }

    private Map<String, Cart> loadCart() {
        Map<String, Cart> map = new HashMap<>();
        File file = new File(filename);
        if (!file.exists()) return map;

        try {
            String content = new String(Files.readAllBytes(file.toPath()));
            if (content.isBlank()) return map;

            JSONObject json = new JSONObject(content);
            for (String username : json.keySet()) {
                JSONArray itemsArray = json.getJSONArray(username);
                Cart cart = new Cart();
                for (int i = 0; i < itemsArray.length(); i++) {
                    JSONObject itemObj = itemsArray.getJSONObject(i);
                    CartItem item = new CartItem(
                            itemObj.getString("productUrl"),
                            itemObj.getString("name"),
                            itemObj.getDouble("price"),
                            itemObj.getInt("quantity")
                    );
                    cart.addItem(
                            item.getProductUrl(),
                            item.getName(),
                            item.getPrice(),
                            item.getQuantity()
                    );
                }
                map.put(username, cart);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return map;
    }

    private void saveCartData() {
        JSONObject json = new JSONObject();
        for (String username : cartData.keySet()) {
            JSONArray arr = new JSONArray();
            for (CartItem item : cartData.get(username).getItems()) {
                JSONObject obj = new JSONObject();
                obj.put("productUrl", item.getProductUrl());
                obj.put("name", item.getName());
                obj.put("price", item.getPrice());
                obj.put("quantity", item.getQuantity());
                arr.put(obj);
            }
            json.put(username, arr);
        }

        try (FileWriter writer = new FileWriter(filename)) {
            writer.write(json.toString(4));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public Cart getCart(String username) {
        return cartData.getOrDefault(username, new Cart());
    }

    @Override
    public void saveCart(String username, Cart cart) {
        cartData.put(username, cart);
        saveCartData();
    }

    public void clearCart(String username) {
        cartData.remove(username);
        saveCartData();
    }
}

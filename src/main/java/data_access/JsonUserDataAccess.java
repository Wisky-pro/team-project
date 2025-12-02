package data_access;

import entity.Cart;
import entity.User;
import org.json.JSONArray;
import org.json.JSONObject;
import use_case.Cart.CartDataAccessInterface;
import use_case.LogIn.LogInUserDataAccessInterface;
import use_case.Signup.SignupUserDataAccessInterface;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

public class JsonUserDataAccess implements
        SignupUserDataAccessInterface,
        LogInUserDataAccessInterface,
        CartDataAccessInterface {

    private final String filePath;
    private final JSONObject root;

    public JsonUserDataAccess(String filePath) {
        this.filePath = filePath;

        try {
            String content = new String(Files.readAllBytes(Paths.get(filePath)));
            root = new JSONObject(content);
        } catch (IOException e) {
            throw new RuntimeException("Could not load users.json", e);
        }
    }

    private void save() {
        try {
            Files.writeString(Paths.get(filePath), root.toString(4));
        } catch (IOException e) {
            throw new RuntimeException("Could not save users.json", e);
        }
    }

    // USER METHODS

    @Override
    public boolean usernameTaken(String username) {
        return root.getJSONObject("users").has(username);
    }

    @Override
    public void saveUser(User user) {
        JSONObject users = root.getJSONObject("users");
        JSONObject jsonUser = new JSONObject();
        jsonUser.put("password", user.getPassword());
        jsonUser.put("cart", new JSONObject().put("items", new JSONArray()));
        users.put(user.getUsername(), jsonUser);
        save();
    }

    @Override
    public void addUser(User user) {
        saveUser(user);
    }

    @Override
    public boolean userExists(String username) {
        return root.getJSONObject("users").has(username);
    }

    @Override
    public boolean isPasswordCorrect(String username, String password) {
        return root.getJSONObject("users")
                .getJSONObject(username)
                .getString("password")
                .equals(password);
    }

    @Override
    public User get(String username) {
        JSONObject u = root.getJSONObject("users").getJSONObject(username);
        return new User(username, u.getString("password"));
    }

    @Override
    public void setCurrentUsername(String username) {
        // optional — used if you want to track session
    }

    // CART METHODS

    @Override
    public Cart getCart(String username) {
        JSONObject user = root.getJSONObject("users").getJSONObject(username);
        JSONObject cartJson = user.getJSONObject("cart");

        Cart cart = new Cart();
        JSONArray items = cartJson.getJSONArray("items");

        for (int i = 0; i < items.length(); i++) {
            JSONObject item = items.getJSONObject(i);
            cart.addItem(
                    item.getString("productURL"),
                    item.getString("name"),
                    item.getDouble("price"),
                    item.getInt("quantity")
                    , 1000
            );
        }

        return cart;
    }

    @Override
    public void saveCart(String username, Cart cart) {
        JSONObject user = root.getJSONObject("users").getJSONObject(username);
        JSONArray items = new JSONArray();

        cart.getItems().forEach(ci -> {
            JSONObject obj = new JSONObject();
            obj.put("productURL", ci.getProductUrl());
            obj.put("name", ci.getName());
            obj.put("price", ci.getPrice());
            obj.put("quantity", ci.getQuantity());
            items.put(obj);
        });

        user.getJSONObject("cart").put("items", items);
        save();
    }
}

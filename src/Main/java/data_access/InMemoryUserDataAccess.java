package data_access;

import entity.User;
import use_case.LogIn.LogInUserDataAccessInterface;
import java.util.HashMap;
import java.util.Map;

public class InMemoryUserDataAccess implements LogInUserDataAccessInterface {

    private final Map<String, User> users = new HashMap<>();
    private String currentUsername;

    @Override
    public boolean userExists(String username) {
        return users.containsKey(username);
    }

    @Override
    public boolean isPasswordCorrect(String username, String password) {
        User user = users.get(username);
        return user != null && user.getPassword().equals(password);
    }

    @Override
    public User get(String username) {
        return users.get(username);
    }

    @Override
    public void setCurrentUsername(String username) {
        this.currentUsername = username;
    }

}

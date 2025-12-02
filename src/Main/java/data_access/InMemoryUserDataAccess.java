package data_access;

import entity.User;
import use_case.LogIn.LogInUserDataAccessInterface;
import use_case.Signup.SignupUserDataAccessInterface;
import use_case.LogOut.LogOutUserDataAccessInterface;

import java.util.HashMap;
import java.util.Map;

public class InMemoryUserDataAccess implements
        LogInUserDataAccessInterface,
        SignupUserDataAccessInterface,
        LogOutUserDataAccessInterface {

    private final Map<String, User> users = new HashMap<>();
    private String currentUsername;

    // ===== LOGIN METHODS =====
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

    public String getCurrentUsername() {
        return currentUsername;
    }

    // ===== SIGNUP METHODS =====
    @Override
    public boolean usernameTaken(String username) {
        return users.containsKey(username);
    }

    @Override
    public void saveUser(User user) {
        users.put(user.getUsername(), user);
    }

    @Override
    public void addUser(User user) {
        users.put(user.getUsername(), user);
    }
}

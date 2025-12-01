package data_access;

import entity.User;
import use_case.LogIn.LogInUserDataAccessInterface;
import use_case.Signup.SignupUserDataAccessInterface;

import java.util.HashMap;
import java.util.Map;

public class InMemoryUserDataAccess implements
        LogInUserDataAccessInterface,
        SignupUserDataAccessInterface {

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

    public String getCurrentUsername() {
        return currentUsername;
    }

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

    public void addUser(String username, String password) {
        users.put(username, new User(username, password));
    }
}

package data_access;

import entity.User;
import entity.UserFactory;
import use_case.LogIn.LogInUserDataAccessInterface;
import use_case.Signup.SignupUserDataAccessInterface;

import java.util.HashMap;
import java.util.Map;

public class FileUserDataAccessObject implements LogInUserDataAccessInterface, SignupUserDataAccessInterface {

    private final Map<String, User> users = new HashMap<>();
    private String currentUsername;
    private final UserFactory userFactory;

    public FileUserDataAccessObject(String filename, UserFactory userFactory) {
        this.userFactory = userFactory;

        // Optional: add a default test user
        User defaultUser = userFactory.createUser("CSC207", "1234");
        users.put(defaultUser.getUsername(), defaultUser);
    }

    // --- Login interface methods ---
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

    // --- Signup interface methods ---
    @Override
    public boolean usernameTaken(String username) {
        return users.containsKey(username);
    }

    @Override
    public void saveUser(User user) {
        users.put(user.getUsername(), user);
    }

    @Override
    public void addUser(User user){
        saveUser(user);
    }

    // Optional helper to access all users
    public Map<String, User> getUserMap() {
        return users;
    }
}

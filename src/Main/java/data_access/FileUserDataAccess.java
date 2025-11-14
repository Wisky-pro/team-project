package data_access;

import entity.User;
import entity.UserFactory;
import use_case.LogIn.LogInUserDataAccessInterface;

import java.util.HashMap;
import java.util.Map;

public class FileUserDataAccess implements LogInUserDataAccessInterface {

    private final Map<String, User> users = new HashMap<>();
    private String currentUsername;
    private final UserFactory userFactory;

    public FileUserDataAccess(String filename, UserFactory userFactory) {
        this.userFactory = userFactory;
        // Optional: add a default test user
        User defaultUser = userFactory.createUser("Kevin", "1234");
        users.put(defaultUser.getUsername(), defaultUser);
    }

    // For adding new users
    public void addUser(User user) {
        users.put(user.getUsername(), user);
    }

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

    // Optional helper to access all users
    public Map<String, User> getUserMap() {
        return users;
    }
}


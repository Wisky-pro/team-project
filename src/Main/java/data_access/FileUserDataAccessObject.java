package data_access;

import entity.User;
import entity.UserFactory;
import use_case.LogIn.LogInUserDataAccessInterface;
import use_case.Signup.SignupUserDataAccessInterface;
import use_case.LogOut.LogOutUserDataAccessInterface;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.util.HashMap;
import java.util.Map;

import org.json.JSONObject;

public class FileUserDataAccessObject implements
        LogInUserDataAccessInterface,
        SignupUserDataAccessInterface,
        LogOutUserDataAccessInterface {

    private final String filename;
    private final Map<String, User> users;
    private String currentUsername;
    private final UserFactory userFactory;

    public FileUserDataAccessObject(String filename, UserFactory userFactory) {
        this.filename = filename;
        this.userFactory = userFactory;

        this.users = loadUsers();

        if (users.isEmpty()) {
            User defaultUser = userFactory.createUser("CSC207", "1234");
            users.put(defaultUser.getUsername(), defaultUser);
            saveUsers();
        }
    }

    private Map<String, User> loadUsers() {
        Map<String, User> map = new HashMap<>();
        File file = new File(filename);
        if (!file.exists()) return map;

        try {
            String content = new String(Files.readAllBytes(file.toPath()));
            JSONObject json = new JSONObject(content);
            for (String username : json.keySet()) {
                JSONObject u = json.getJSONObject(username);
                User user = new User(u.getString("username"), u.getString("password"));
                map.put(username, user);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return map;
    }

    private void saveUsers() {
        JSONObject json = new JSONObject();
        for (User u : users.values()) {
            JSONObject userObj = new JSONObject();
            userObj.put("username", u.getUsername());
            userObj.put("password", u.getPassword());
            json.put(u.getUsername(), userObj);
        }
        try (FileWriter writer = new FileWriter(filename)) {
            writer.write(json.toString(4));
        } catch (IOException e) {
            e.printStackTrace();
        }
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
        saveUsers();
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
        saveUsers();
    }

    @Override
    public void addUser(User user) {
        saveUser(user);
    }

    public Map<String, User> getUserMap() {
        return users;
    }
}

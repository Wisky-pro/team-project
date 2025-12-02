package entity;

public class User {

    private final String name;
    private final String password;

    public User(String name, String password) {
        if ("".equals(name)) { // name is empty
            throw new IllegalArgumentException("Username cannot be empty");
        }
        if ("".equals(password)) { // password is empty
            throw new IllegalArgumentException("Password cannot be empty");
        }
        this.name = name;
        this.password = password;
    }

    public String getUsername() {
        return name;
    }

    public String getPassword() {
        return password;
    }

}


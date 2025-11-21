package use_case.Signup;

import entity.User;

public interface SignupUserDataAccessInterface {

    boolean usernameTaken(String username);

    void saveUser(User user);

    void addUser(User user);

    User get(String username);

    void setCurrentUsername(String username);
}


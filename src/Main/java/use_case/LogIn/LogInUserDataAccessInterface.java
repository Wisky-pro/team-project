package use_case.LogIn;
import entity.User;


public interface LogInUserDataAccessInterface {

    boolean userExists(String username);

    boolean isPasswordCorrect(String username, String password);

    User get(String username);

    void setCurrentUsername(String username);
}



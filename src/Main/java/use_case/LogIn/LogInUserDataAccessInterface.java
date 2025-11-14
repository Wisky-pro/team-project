package use_case.LogIn;
import entity.User;


public interface LogInUserDataAccessInterface {

    boolean existsByName(String username);

    User get(String username);

    void setCurrentUsername(String username);
}



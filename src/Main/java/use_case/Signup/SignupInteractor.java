package use_case.Signup;

import entity.User;
import entity.UserFactory;
import java.util.ArrayList;
import java.util.List;

public class SignupInteractor implements SignupInputBoundary {

    private final SignupUserDataAccessInterface userDataAccess;
    private final SignupOutputBoundary presenter;
    private final UserFactory userFactory;

    public SignupInteractor(SignupUserDataAccessInterface userDataAccess,
                            SignupOutputBoundary presenter,
                            UserFactory userFactory) {
        this.userDataAccess = userDataAccess;
        this.presenter = presenter;
        this.userFactory = userFactory;
    }

    @Override
    public void execute(SignupInputData inputData) {
        String username = inputData.getUsername();
        String password = inputData.getPassword();
        List<String> errors = new ArrayList<>();

        if (username == null || username.isEmpty()) {
            errors.add("Username cannot be empty");
        }
        if (password == null || password.isEmpty()) {
            errors.add("Password cannot be empty");
        }
        if (userDataAccess.usernameTaken(username)) {
            errors.add("Username already exists: " + username);
        }

        if (!errors.isEmpty()) {
            presenter.presentSignupFailure(errors);
            return;
        }

        // Safe to create the user
        User newUser = userFactory.createUser(username, password);
        userDataAccess.addUser(newUser);

        presenter.presentSignupSuccess(new SignupOutputData(newUser.getUsername()));
    }
}

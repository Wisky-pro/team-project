package use_case.Signup;

import entity.User;
import entity.UserFactory;

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

        if (username == null || username.isBlank()) {
            presenter.prepareFailView("Username cannot be empty");
            return;
        }
        if (password == null || password.isBlank()) {
            presenter.prepareFailView("Password cannot be empty");
            return;
        }

        if (userDataAccess.usernameTaken(username)) {
            presenter.prepareFailView("Username already exists: " + username);
            return;
        }

        User newUser = userFactory.createUser(username, password);
        userDataAccess.addUser(newUser);

        presenter.prepareSuccessView(new SignupOutputData(newUser.getUsername()));
    }
}

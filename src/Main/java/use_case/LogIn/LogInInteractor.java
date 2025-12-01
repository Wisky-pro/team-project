package use_case.LogIn;

import entity.User;

public class LogInInteractor implements LogInInputBoundary {

    private final LogInUserDataAccessInterface userDataAccessObject;
    private final LogInOutputBoundary logInPresenter;

    public LogInInteractor(LogInUserDataAccessInterface userDataAccessObject,
                           LogInOutputBoundary logInPresenter) {
        this.userDataAccessObject = userDataAccessObject;
        this.logInPresenter = logInPresenter;
    }

    @Override
    public void execute(LogInInputData inputData) {
        String username = inputData.getUsername();
        String password = inputData.getPassword();

        if (username.isEmpty()) {
            logInPresenter.prepareFailView("Username cannot be empty");
            return;
        }

        if (password.isEmpty()) {
            logInPresenter.prepareFailView("Password cannot be empty");
            return;
        }

        if (!userDataAccessObject.userExists(username)) {
            logInPresenter.prepareFailView("Invalid username or password");
            return;
        }

        if (!userDataAccessObject.isPasswordCorrect(username, password)) {
            logInPresenter.prepareFailView("Invalid username or password");
            return;
        }

        User user = userDataAccessObject.get(username);
        userDataAccessObject.setCurrentUsername(username);

        LogInOutputData outputData = new LogInOutputData(user.getUsername());
        logInPresenter.prepareSuccessView(outputData);
    }
}

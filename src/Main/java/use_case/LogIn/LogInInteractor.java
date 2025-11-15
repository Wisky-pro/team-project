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

        // Check if user exists
        if (!userDataAccessObject.userExists(username)) {
            logInPresenter.prepareFailView(username + ": account does not exist.");
            return;
        }

        // Validate password
        if (!userDataAccessObject.isPasswordCorrect(username, password)) {
            logInPresenter.prepareFailView("Incorrect password for \"" + username + "\".");
            return;
        }

        // Fetch user
        User user = userDataAccessObject.get(username);

        // Update current user
        userDataAccessObject.setCurrentUsername(username);

        // Prepare output data
        LogInOutputData outputData = new LogInOutputData(user.getUsername());

        // Send success to presenter
        logInPresenter.prepareSuccessView(outputData);
    }
}

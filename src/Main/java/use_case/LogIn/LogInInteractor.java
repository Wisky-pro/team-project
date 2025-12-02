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

        if (!userDataAccessObject.userExists(username)) {
            logInPresenter.prepareFailView(username + ": account does not exist.");
            return;
        }

        if (!userDataAccessObject.isPasswordCorrect(username, password)) {
            logInPresenter.prepareFailView("Incorrect password for \"" + username + "\".");
            return;
        }

        User user = userDataAccessObject.get(username);

        userDataAccessObject.setCurrentUsername(username);

        LogInOutputData outputData = new LogInOutputData(user.getUsername());

        logInPresenter.prepareSuccessView(outputData);
    }
}

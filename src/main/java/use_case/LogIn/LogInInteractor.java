package use_case.LogIn;

public class LogInInteractor implements LogInInputBoundary {

    private final LogInUserDataAccessInterface userDataAccess;
    private final LogInOutputBoundary presenter;

    public LogInInteractor(LogInUserDataAccessInterface userDataAccess,
                           LogInOutputBoundary presenter) {
        this.userDataAccess = userDataAccess;
        this.presenter = presenter;
    }

    @Override
    public void execute(LogInInputData inputData) {
        String username = inputData.getUsername();
        String password = inputData.getPassword();

        // Validate empty fields
        if (username == null || username.isBlank()) {
            presenter.prepareFailView("Username cannot be empty");
            return;
        }

        if (password == null || password.isBlank()) {
            presenter.prepareFailView("Password cannot be empty");
            return;
        }

        // Check user exists and password matches
        if (!userDataAccess.userExists(username) ||
            !userDataAccess.isPasswordCorrect(username, password)) {
            presenter.prepareFailView("Invalid username or password");
            return;
        }

        // Success
        LogInOutputData outputData = new LogInOutputData(username);
        presenter.prepareSuccessView(outputData);
    }
}

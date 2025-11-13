package interface_adapter.LogIn;

import use_case.LogIn.LogInInputBoundary;
import use_case.LogIn.LogInInputData;

public class LogInController {
    private final LogInInputBoundary logInUseCaseInteractor;

    public LogInController(LogInInputBoundary logInUseCaseInteractor) {
        this.logInUseCaseInteractor = logInUseCaseInteractor;
    }


    public void execute(String username, String password) {
        final LogInInputData logInInputData = new LogInInputData(username, password);
        logInUseCaseInteractor.execute(logInInputData);
    }
}


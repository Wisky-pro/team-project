package interface_adapter.LogIn;

import use_case.LogIn.LogInInputData;
import use_case.LogIn.LogInInputBoundary;

public class LogInController {

    private final LogInInputBoundary interactor;

    public LogInController(LogInInputBoundary interactor) {
        this.interactor = interactor;
    }

    public void login(String username, String password) {
        interactor.execute(new LogInInputData(username, password));
    }
}

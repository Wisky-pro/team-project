package interface_adapter.Logout;

import use_case.LogOut.LogOutInputBoundary;

public class LogoutController {

    private final LogOutInputBoundary interactor;

    public LogoutController(LogOutInputBoundary interactor) {
        this.interactor = interactor;
    }

    public void logout() {
        interactor.execute();
    }
}

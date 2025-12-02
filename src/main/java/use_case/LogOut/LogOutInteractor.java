package use_case.LogOut;

import data_access.InMemoryUserDataAccess;

public class LogOutInteractor implements LogOutInputBoundary{
    private final LogOutOutputBoundary presenter;
    private final InMemoryUserDataAccess userDataAccess;

    public LogOutInteractor(LogOutOutputBoundary presenter, InMemoryUserDataAccess userDataAccess) {
        this.presenter = presenter;
        this.userDataAccess = userDataAccess;
    }

    @Override
    public void execute() {
        userDataAccess.setCurrentUsername(null);
        presenter.prepareSuccessView();
    }
}

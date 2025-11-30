package use_case.LogOut;

import data_access.InMemoryUserDataAccess;

public class LogOutInteractor implements LogOutInputBoundary {

    private final LogOutOutputBoundary presenter;
    private final InMemoryUserDataAccess userDAO;

    public LogOutInteractor(LogOutOutputBoundary presenter,
                            InMemoryUserDataAccess userDAO) {
        this.presenter = presenter;
        this.userDAO = userDAO;
    }

    @Override
    public void execute() {
        userDAO.setCurrentUsername(null);
        presenter.prepareSuccessView();
    }
}

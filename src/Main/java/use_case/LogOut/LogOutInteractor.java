package use_case.LogOut;

public class LogOutInteractor implements LogOutInputBoundary {

    private final LogOutOutputBoundary presenter;
    private final LogOutUserDataAccessInterface userDAO; // now uses interface

    public LogOutInteractor(LogOutOutputBoundary presenter,
                            LogOutUserDataAccessInterface userDAO) {
        this.presenter = presenter;
        this.userDAO = userDAO;
    }

    @Override
    public void execute() {
        userDAO.setCurrentUsername(null);
        presenter.prepareSuccessView();
    }
}

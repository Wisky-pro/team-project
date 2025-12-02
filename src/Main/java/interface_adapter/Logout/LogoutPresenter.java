package interface_adapter.Logout;

import interface_adapter.ViewManagerModel;
import interface_adapter.LogIn.LogInViewModel;
import use_case.LogOut.LogOutOutputBoundary;

public class LogoutPresenter implements LogOutOutputBoundary {

    private final ViewManagerModel viewManagerModel;
    private final LogInViewModel logInViewModel;

    public LogoutPresenter(ViewManagerModel viewManagerModel,
                           LogInViewModel logInViewModel) {
        this.viewManagerModel = viewManagerModel;
        this.logInViewModel = logInViewModel;
    }

    @Override
    public void prepareSuccessView() {
        logInViewModel.reset();
        viewManagerModel.setActiveView("login");
        viewManagerModel.firePropertyChanged();
    }
}

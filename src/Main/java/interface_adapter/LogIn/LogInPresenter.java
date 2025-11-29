package interface_adapter.LogIn;

import interface_adapter.ViewManagerModel;
import use_case.LogIn.LogInOutputBoundary;
import use_case.LogIn.LogInOutputData;

public class LogInPresenter implements LogInOutputBoundary {

    private final LogInViewModel loginViewModel;
    private final ViewManagerModel viewManagerModel;

    public LogInPresenter(LogInViewModel loginViewModel,
                          ViewManagerModel viewManagerModel) {
        this.loginViewModel = loginViewModel;
        this.viewManagerModel = viewManagerModel;
    }

    @Override
    public void prepareSuccessView(LogInOutputData data) {
        // On successful login, switch to the price tracker screen
        viewManagerModel.setActiveView("priceTracker");
        viewManagerModel.firePropertyChanged();
    }

    @Override
    public void prepareFailView(String errorMessage) {
        loginViewModel.setMessage(errorMessage);
        loginViewModel.firePropertyChanged();
    }
}

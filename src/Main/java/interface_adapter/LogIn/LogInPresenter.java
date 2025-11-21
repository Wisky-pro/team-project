package interface_adapter.LogIn;

import interface_adapter.Dashboard.DashboardViewModel;
import interface_adapter.ViewManagerModel;
import use_case.LogIn.LogInOutputBoundary;
import use_case.LogIn.LogInOutputData;

public class LogInPresenter implements LogInOutputBoundary {

    private final LogInViewModel loginViewModel;
    private final DashboardViewModel dashboardViewModel;
    private final ViewManagerModel viewManagerModel;

    public LogInPresenter(LogInViewModel loginViewModel,
                          DashboardViewModel dashboardViewModel,
                          ViewManagerModel viewManagerModel) {
        this.loginViewModel = loginViewModel;
        this.dashboardViewModel = dashboardViewModel;
        this.viewManagerModel = viewManagerModel;
    }

    @Override
    public void prepareSuccessView(LogInOutputData data) {
        dashboardViewModel.setMessage("Logged in as: " + data.getUsername());
        dashboardViewModel.firePropertyChanged();

        viewManagerModel.setActiveView("dashboard");
        viewManagerModel.firePropertyChanged();
    }

    @Override
    public void prepareFailView(String errorMessage) {
        loginViewModel.setMessage(errorMessage);
        loginViewModel.firePropertyChanged();
    }
}

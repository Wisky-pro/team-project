package interface_adapter.LogIn;

import use_case.LogIn.LogInOutputBoundary;
import use_case.LogIn.LogInOutputData;

public class LogInPresenter implements LogInOutputBoundary {

    private final LogInViewModel viewModel;

    public LogInPresenter(LogInViewModel viewModel) {
        this.viewModel = viewModel;
    }

    @Override
    public void prepareSuccessView(LogInOutputData data) {
        viewModel.setSuccessMessage("Welcome, " + data.getUsername());
        viewModel.setLoggedIn(true);
        // In a real system: notify observers or fire property change
    }

    @Override
    public void prepareFailView(String error) {
        viewModel.setErrorMessage(error);
        viewModel.setLoggedIn(false);
        // Notify observers if needed
    }
}

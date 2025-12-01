package interface_adapter.Signup;

import interface_adapter.ViewManagerModel;
import use_case.Signup.SignupOutputBoundary;
import use_case.Signup.SignupOutputData;

import java.util.List;

public class SignupPresenter implements SignupOutputBoundary {

    private final SignupViewModel signupViewModel;
    private final ViewManagerModel viewManagerModel;

    public SignupPresenter(SignupViewModel signupViewModel, ViewManagerModel viewManagerModel) {
        this.signupViewModel = signupViewModel;
        this.viewManagerModel = viewManagerModel;
    }

    @Override
    public void prepareSuccessView(SignupOutputData data) {
        signupViewModel.setSuccessMessage("Signup successful: " + data.getUsername());
        viewManagerModel.setActiveView("login");
        viewManagerModel.firePropertyChanged();
    }

    @Override
    public void prepareFailView(String error) {
        signupViewModel.setErrorMessage(error);
        signupViewModel.firePropertyChanged();
    }

    @Override
    public void presentSignupSuccess(SignupOutputData outputData) {
        // Redirect to the same logic as prepareSuccessView
        prepareSuccessView(outputData);
    }

    @Override
    public void presentSignupFailure(List<String> errors) {
        // Combine errors into a single string and redirect to prepareFailView
        String combined = String.join(", ", errors);
        prepareFailView(combined);
    }
}

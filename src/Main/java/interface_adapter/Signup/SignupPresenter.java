package interface_adapter.Signup;

import interface_adapter.ViewManagerModel;
import use_case.Signup.SignupOutputBoundary;
import use_case.Signup.SignupOutputData;

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
}

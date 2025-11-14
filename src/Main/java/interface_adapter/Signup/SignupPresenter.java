package interface_adapter.Signup;

import use_case.Signup.SignupOutputBoundary;
import use_case.Signup.SignupOutputData;

public class SignupPresenter implements SignupOutputBoundary {

    private final SignupViewModel viewModel;

    public SignupPresenter(SignupViewModel viewModel) {
        this.viewModel = viewModel;
    }

    @Override
    public void prepareSuccessView(SignupOutputData outputData) {
        // Update the ViewModel with a success message
        viewModel.setSuccessMessage("Account created successfully: " + outputData.getUsername());
    }

    @Override
    public void prepareFailView(String errorMessage) {
        // Update the ViewModel with an error message
        viewModel.setErrorMessage(errorMessage);
    }
}


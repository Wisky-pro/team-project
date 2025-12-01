package use_case.Signup;

import java.util.List;

public interface SignupOutputBoundary {

    void prepareSuccessView(SignupOutputData outputData);

    void prepareFailView(String errorMessage);

    void presentSignupSuccess(SignupOutputData outputData);

    void presentSignupFailure(List<String> errors);
}
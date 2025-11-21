package use_case.Signup;

public interface SignupOutputBoundary {

    void prepareSuccessView(SignupOutputData outputData);

    void prepareFailView(String errorMessage);
}
package use_case.LogIn;

public interface LogInOutputBoundary {

    void prepareSuccessView(LogInOutputData outputData);


    void prepareFailView(String errorMessage);
}

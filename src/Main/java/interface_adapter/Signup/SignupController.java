package interface_adapter.Signup;

import use_case.Signup.SignupInputBoundary;
import use_case.Signup.SignupInputData;

public class SignupController {

    private final SignupInputBoundary interactor;

    public SignupController(SignupInputBoundary interactor) {
        this.interactor = interactor;
    }


    public void signup(String username, String password) {
        SignupInputData inputData = new SignupInputData(username, password);
        interactor.execute(inputData);
    }
}


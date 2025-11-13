package use_case.LogIn;

public class LogInInteractor implements LogInInputBoundary {

    @Override
    public void execute(LogInInputData inputData) {
        String username = inputData.getUsername();
        String password = inputData.getPassword();

        System.out.println("Attempting login for: " + username);
    }
}

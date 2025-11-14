package use_case.LogIn;

public class LogInOutputData {
    private final String username;

    public LogInOutputData(String username) {
        this.username = username;
    }

    public String getUsername() {
        return username;
    }

    public String getUser() {
        return null;
    }
}


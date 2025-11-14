package interface_adapter.Logged_in;

public class LoggedInState {

    private String username;


    public LoggedInState(String username) {
        this.username = username;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }
}

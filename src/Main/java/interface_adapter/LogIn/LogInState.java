package interface_adapter.LogIn;

public class LogInState {
    private String username = "";
    private String password = "";
    private String error = "";

    public String getUsername() { return username; }
    public void setUsername(String u) { this.username = u; }

    public String getPassword() { return password; }
    public void setPassword(String p) { this.password = p; }

    public String getError() { return error; }
    public void setError(String e) { this.error = e; }
}

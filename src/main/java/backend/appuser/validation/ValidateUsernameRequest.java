package backend.appuser.validation;

public class ValidateUsernameRequest {
    private String username;

    // All args constructor used to map the request
    public ValidateUsernameRequest(String username) { this.username = username; }

    // Explicit default constructor needed to map request
    public ValidateUsernameRequest() {}

    // Getter
    public String getUsername() { return username; }
}

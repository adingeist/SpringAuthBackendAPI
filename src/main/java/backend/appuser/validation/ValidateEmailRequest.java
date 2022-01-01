package backend.appuser.validation;

public class ValidateEmailRequest {
    private String email;

    // All args constructor used to map the request
    public ValidateEmailRequest(String email) { this.email = email; }

    // Explicit default constructor needed to map request
    public ValidateEmailRequest() {}

    // Getter
    public String getEmail() { return email; }
}

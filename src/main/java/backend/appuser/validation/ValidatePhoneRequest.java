package backend.appuser.validation;

public class ValidatePhoneRequest {
    private String phone;

    // All args constructor used to map the request
    public ValidatePhoneRequest(String phone) { this.phone = phone; }

    // Explicit default constructor needed to map request
    public ValidatePhoneRequest() {}

    // Getter
    public String getPhone() { return phone; }
}

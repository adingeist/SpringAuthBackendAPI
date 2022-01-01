package backend.registration.code;

// Requests to verify a email/phone number are mapped to an object built by this class
public class SubmitCodeRequest {
    private String itemToVerify;
    private String code;

    // Constructor
    public SubmitCodeRequest(String itemToVerify, String code) {
        this.itemToVerify = itemToVerify;
        this.code = code;
    }

    public SubmitCodeRequest() {}

    // Getters
    public String getItemToVerify() {
        return itemToVerify;
    }

    public String getCode() {
        return code;
    }
}

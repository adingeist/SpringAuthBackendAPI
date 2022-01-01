package backend.registration.code;


// Requests to send a verification are mapped to an object built by this class
public class SendCodeRequest {
    private String itemToVerify;

    // All args constructor
    public SendCodeRequest(String itemToVerify) {
        this.itemToVerify = itemToVerify;
    }

    public SendCodeRequest() {}

    // Getter
    public String getItemToVerify() {
        return itemToVerify;
    }
}

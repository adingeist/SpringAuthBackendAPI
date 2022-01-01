package backend.registration.code;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.mail.MessagingException;

@RestController // Listens for POST GET PUT and DELETE requests
@RequestMapping(path = "api/v1/registration/code") // Listens at this address
//@AllAr/**/gsConstructor // Creates an all args constructor
public class ConfirmationCodeController {
    private final ConfirmationCodeService confirmationCodeService;

    public ConfirmationCodeController(ConfirmationCodeService confirmationCodeService) {
        this.confirmationCodeService = confirmationCodeService;
    }

    // POST : JSON {"itemToConfirm" : "adingeist@me.com"}
    // Listens for POST requests that will sent a confirmation code to a given email/phone number
    @PostMapping(path = "/send") // Listens at api/v1/registration/code
    // Maps the request to a SendCodeRequest object. This function acknowledges it may throw a MessagingException,
    // which we are okay with.
    public String sendConfirmation(@RequestBody SendCodeRequest request) throws MessagingException {
        System.out.println("SEND CODE TO: " + request.getItemToVerify());
        return confirmationCodeService.saveConfirmationCode(request.getItemToVerify());
    }

    // POST : JSON    {"itemToConfirm":"adingeist@me.com" , "code":"077613"}
    // Listens for POST request that will verify an email/phone number if the code matches the corresponding code
    // in the database.
    @PostMapping(path = "/submit") // Listens at api/v1/registration/submit
    public String verifyConfirmationCode(@RequestBody SubmitCodeRequest confirmationRequest) {
        return confirmationCodeService.verifyConfirmationCode(confirmationRequest);
    }
}

package backend.registration.code;

import backend.appuser.AppUserService;
import backend.exception.ConflictException;
import backend.registration.code.send.email.EmailMessage;
import backend.registration.code.send.email.SendEmail;
import backend.registration.code.send.sms.SendSMS;
import org.springframework.stereotype.Service;

import javax.mail.MessagingException;
import java.security.SecureRandom;
import java.time.LocalDateTime;

@Service // handles the business logic for confirmation-code-related request
public class ConfirmationCodeService {
    private AppUserService appUserService;
    private ConfirmationCodeRepository confirmationCodeRepository;
    private SendSMS sendSMS;
    private SendEmail sendEmail;
    private EmailMessage emailMessage;

    public ConfirmationCodeService(AppUserService appUserService, ConfirmationCodeRepository confirmationCodeRepository, SendSMS sendSMS, SendEmail sendEmail, EmailMessage emailMessage) {
        this.appUserService = appUserService;
        this.confirmationCodeRepository = confirmationCodeRepository;
        this.sendSMS = sendSMS;
        this.sendEmail = sendEmail;
        this.emailMessage = emailMessage;
    }

    // Saves a confirmation code to the database with an associated email/phone number and sends it to the address
    public String saveConfirmationCode(String itemToConfirm) throws MessagingException {
        // Validate that the email/phone number isn't associated with an account. This prevents users from un-verifying
        // their account credentials.
        if (itemToConfirm.contains("@")) appUserService.validateEmail(itemToConfirm);
        else appUserService.validatePhone(itemToConfirm);
        // Generate code 000000-99999
        SecureRandom random = new SecureRandom();
        String code = String.format("%06d",random.nextInt(1000000));
        // Create a new confirmation code object containing the code, it corresponding item to verify, time of creation,
        // and it's expiration time set 15 minutes after creation.
        ConfirmationCode confirmationCode = new ConfirmationCode(
                itemToConfirm,
                code,
                LocalDateTime.now(),
                LocalDateTime.now().plusMinutes(15)
        );
        // Destroy any old codes that were attempting to verify this email/phone
        confirmationCodeRepository.deleteItems(confirmationCode.getItemToVerify());
        confirmationCodeRepository.save(confirmationCode); // Save code to database
        // Check if the item to verify is an email or phone number
        if (itemToConfirm.contains("@")) {
            // Is email --> Send email via gmail; throws exception on failure
            sendEmail.send(itemToConfirm, emailMessage.buildEmail(code));
        } else {
            // Is phone # --> Send SMS via twilio; throws exception on failure
            sendSMS.send(itemToConfirm, code);
        }
        return "Code sent to " + itemToConfirm + "."; // Return a success message.
    }

    // Compares a provided confirmation to the one in the database to the given email/phone number. If they match,
    // the item is verified. Otherwise, an exception is thrown.
    public String verifyConfirmationCode(SubmitCodeRequest confirmationRequest) {
        // Throws 409 user error if email/phone isn't found
        if (confirmationCodeRepository.findByItemToVerify(confirmationRequest.getItemToVerify()).isEmpty())
            throw new ConflictException("There's no code associated with that item.");
        // A code exists. Get it from the repository and store it in a ConfirmationCode object.
        ConfirmationCode result = confirmationCodeRepository.
                findByItemToVerify(confirmationRequest.getItemToVerify()).get();
        // If the code is expired, throw an exception.
        if (LocalDateTime.now().isAfter(result.getExpiresAt()))
            throw new ConflictException("The code entered has expired.");
        // If the provided code and the code in the database match, set the confirmedAt time to the current time
        if (result.getCode().equals(confirmationRequest.getCode())) {
            confirmationCodeRepository.updateConfirmedAt(LocalDateTime.now(), confirmationRequest.getItemToVerify());
            return confirmationRequest.getItemToVerify() + " was verified.";
        } else { // Codes don't match
            throw new ConflictException("Confirmation code was invalid.");
        }
    }
}

package backend.registration.code.send.sms;

import com.twilio.rest.api.v2010.account.Message;
import com.twilio.rest.api.v2010.account.MessageCreator;
import com.twilio.type.PhoneNumber;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
public class SendSMS {
    private Logger LOGGER = LoggerFactory.getLogger(SendSMS.class); // logs detailed messages to the console

    // Method to send a text message containing the user's confirmation code
    public void send(String receiverNumber, String code) {
        String MESSAGE_BODY = "Your Niblet verification code is: " + code; // create the message content
        PhoneNumber to = new PhoneNumber(receiverNumber); // Construct a phone number with the phone # String
        PhoneNumber from = new PhoneNumber("+19166940519"); // Sender number

        MessageCreator creator = Message.creator( // Create the message
                to,
                from,
                MESSAGE_BODY
        );
        creator.create(); // Send the text message
        LOGGER.info("Sent an SMS to: " + receiverNumber);
    }
}

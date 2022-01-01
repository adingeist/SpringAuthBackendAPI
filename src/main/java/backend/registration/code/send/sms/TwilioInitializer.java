package backend.registration.code.send.sms;

import com.twilio.Twilio;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;

@Configuration
public class TwilioInitializer {
    // Creates a logger for detailed console log messages
    private static Logger LOGGER = LoggerFactory.getLogger(TwilioInitializer.class);

    @Autowired
    public TwilioInitializer() {
        // Login and connect to Twilio API
        Twilio.init(
                "AC1e97468a1b97d2aebe260235182a0886",
                "5cb9ec1beeb5a698a9b44ecc7aaefaba"
        );
        LOGGER.info("Twilio initialized.");
    }
}
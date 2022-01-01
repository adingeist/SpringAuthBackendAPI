package backend.registration.code.send.email;

import org.springframework.context.annotation.Configuration;

import javax.activation.DataHandler;
import javax.activation.FileDataSource;
import javax.mail.MessagingException;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMultipart;
import java.io.File;

// followed guide from reals how to

@Configuration // Says this class can be used to configure parts of our backend. In this case, configure the verification email.
public class EmailMessage {
    public MimeMultipart buildEmail(String code) throws MessagingException {
        MimeMultipart multipart = new MimeMultipart("related"); // Creates a multi-part object that will hold
        // two parts: HTML data and image data.

        // Create the HTML body part
        MimeBodyPart messageBodyPart = new MimeBodyPart();
        String htmlText =
            "<html>" +
              "<body" +
                    "style=\"" +
                    "font-family: arial, 'helvetica neue', helvetica, sans-serif;" +
                    "\"" +
              ">" +
                "<img " +
                    "src=\"cid:image\"/ " +
                    "alt=\"Logo\" title=\"Logo\" " +
                    "style=\"display:block; height:81\" " +
                    "width=\"269\" " +
                    "height=\"81\"" +
                "/>" +
                "<h3>You're almost ready to join!</h3>" +
                "<div style=\"height: 5px; width: 50%; background-color: green\"></div>" +
                "<p>Please enter this code in the app:</p>" +

                "<div style=\"text-align: center; background-color: black; width: 100px; padding: 1px\">" +
                  "<h2 style=\"color: white\">" + code + "</h2>" +
                "</div>" +

                "<p style=\"max-width: 300px\">" +
                    "This code will expire in 15 minutes or if another code is requested to" +
                    " this address." +
                "</p>" +
              "</body>" +
            "</html>";
        messageBodyPart.setContent(htmlText, "text/html"); // Say what content is in this body part
        multipart.addBodyPart(messageBodyPart); // Add the HTML part into the multipart

        // Second body part adds image data
        messageBodyPart = new MimeBodyPart();
        File emailImage = new File("src/main/assets/niblet-text-logo-orange.png"); // Niblet image
        FileDataSource dataSource = new FileDataSource(emailImage); // Obtains the source of the image
        messageBodyPart.setDataHandler(new DataHandler(dataSource)); // Add the image data
        messageBodyPart.setHeader("Content-ID", "<image>"); // Says this part contains image data
        multipart.addBodyPart(messageBodyPart); // Add the image body part to the multipart

        return multipart; // Return the finished email containing HTML and image data
    }
}

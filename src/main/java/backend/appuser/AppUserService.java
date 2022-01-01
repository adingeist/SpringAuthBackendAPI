package backend.appuser;

import backend.exception.ConflictException;
import backend.registration.code.ConfirmationCodeRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service // indicates this layer handles business logic.
//@AllArgsConstructor // generates a constructor taking all class fields as parameters

// This class implements UserDetailsService because this implementation gives a method to securely find users when
// wants to login.
public class AppUserService implements UserDetailsService {
    private AppUserRepository appUserRepository;
    private BCryptPasswordEncoder bCryptPasswordEncoder;
    private ConfirmationCodeRepository confirmationCodeRepository;

    public AppUserService(AppUserRepository appUserRepository, BCryptPasswordEncoder bCryptPasswordEncoder, ConfirmationCodeRepository confirmationCodeRepository) {
        this.appUserRepository = appUserRepository;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
        this.confirmationCodeRepository = confirmationCodeRepository;
    }

    // Validates any registration request before saving it to the server.
    public String signUpUser(AppUser appUser) {
        // Check if the request contains an email to verify
        if (appUser.getEmail() != null) {
            // Check if the email isn't used in another account and has the format of an email address
            this.validateEmail(appUser.getEmail());
            // If the confirmation code is not confirmed, throw an exception
            if (confirmationCodeRepository.findByItemToVerify(appUser.getEmail()).isEmpty() ||
                    confirmationCodeRepository.findByItemToVerify(appUser.getEmail()).get().getConfirmedAt() == null)
                throw new ConflictException("Email has not been verified");
        }
        // Check if the request contains a phone number to verify
        if (appUser.getPhone() != null) {
            // Check if the phone isn't used in another account and has the format of a phone number
            this.validateEmail(appUser.getPhone());
            // If the confirmation code is not confirmed, throw an exception
            if (confirmationCodeRepository.findByItemToVerify(appUser.getPhone()).isEmpty() ||
                    confirmationCodeRepository.findByItemToVerify(appUser.getPhone()).get().getConfirmedAt() == null)
                throw new ConflictException("Phone number has not been verified");
        }
        this.validateUsername(appUser.getUsername()); // Validate the username
        String encodedPassword = bCryptPasswordEncoder.encode(appUser.getPassword()); // Encrypt the user's password
        appUser.setPassword(encodedPassword); // Update the user's password to the encrypted password
        appUserRepository.save(appUser); // Tell the repository to save the user to the database.
        return "User registered."; // Return a status 200 code (OK) with the message, "User registered."
    }

    // Validate the an email. Check if the string is formatted like an email and does not exist in the database.
    public String validateEmail(String email) {
        // Check if the email has valid email address characters. If not throw an exception.
        boolean hasValidCharacters = email.matches("^[0-9a-zA-Z_.@!#$%&'*+/=?^`{|}~-]*$");
        if (!hasValidCharacters) throw new ConflictException("Invalid email address.");
        // Check if the email address is already used by another account.
        boolean userExists = appUserRepository.findByEmail(email).isPresent();
        if (userExists) throw new ConflictException("An account with that email already exists.");
        return "Email is ok.";
    }

    // Validate the phone number. Check if the string is formatted like a phone number and does not exist in the database.
    public String validatePhone(String phone) {
        // Check if the phone number has valid characters.
        boolean hasValidCharacters = phone.matches("^[0-9]*$");
        if (!hasValidCharacters) throw new ConflictException("Invalid phone number.");
        // Throw an exception if the phone number is used by another user.
        boolean phoneExists = appUserRepository.findByPhone(phone).isPresent();
        if (phoneExists) throw new ConflictException("An account with that phone number already exists.");
        return "Phone number is ok.";
    }

    // Validate the username. Check if the username contains valid characters and is not taken by another user.
    public String validateUsername(String username) {
        // Check for valid characters
        boolean hasValidCharacters = username.matches("^[a-zA-Z0-9._]*$");
        if (!hasValidCharacters) throw new ConflictException("Invalid username.");
        // Check if username already exists
        boolean usernameExists = appUserRepository.findByUsername(username).isPresent();
        if (usernameExists) throw new ConflictException("An account with that username already exists.");
        return "Username is ok.";
    }

    // Essential for security. This method would be used for admins trying to access the server. However, exclusive
    // admin permissions are not set up.
    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        return appUserRepository.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException(
                        String.format("User with email %s not found", email)));
    }


}

package backend.appuser;

import backend.appuser.validation.ValidateEmailRequest;
import backend.appuser.validation.ValidatePhoneRequest;
import backend.appuser.validation.ValidateUsernameRequest;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController // indicates this will be listening for CRUD requests
@RequestMapping(path = "api/v1/registration") // where the this will listen for requests
public class AppUserController {
    private AppUserService appUserService;

    // Constructor
    public AppUserController(AppUserService appUserService) {
        this.appUserService = appUserService;
    }

    /* POST : JSON
            {
                "name" : "Adin",
                "email" : "adingeist@me.com",
                "phone" : "15709861234",
                "username" : "adingeist",
                "password" : "password"
            }
        */
    // Listens for POST requests at api/v1/registration, maps the request body into an AppUser object and then calls
    // the service layer.
    @PostMapping
    public String register(@RequestBody AppUser request) {
        request.setAppUserRole(AppUserRole.USER);
        return appUserService.signUpUser(request); // Return a success message or throw an exception (Bad request)
    }

    // POST :   "email" : "adingeist@me.com"
    // Listens at api/v1/registration/email, and maps the request body to a ValidateEmailRequest object
    @PostMapping(path = "/email")
    public String validateEmail(@RequestBody ValidateEmailRequest request) {
        return appUserService.validateEmail(request.getEmail()); // Returns email is ok or throws an exception
    }

    // POST :   "phone" : "15702551893"
    // Listens for POST requests to validate an email by calling the service layer
    @PostMapping(path = "/phone")
    public String validatePhone(@RequestBody ValidatePhoneRequest request) {
        return appUserService.validatePhone(request.getPhone()); // Returns phone is ok or throws an exception
    }

    // POST: "username" : "adingeist"
    // Listens for POST requests at api/v1/registration/username and maps a request to a ValidateUsernameRequest
    // object. This object is passed to the service layer for validation.
    @PostMapping(path = "/username")
    public String validateUsername(@RequestBody ValidateUsernameRequest request) {
        return appUserService.validateUsername(request.getUsername()); // Returns username is ok or throws an exception
    }
}

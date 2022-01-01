package backend.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

// A 409 error (CONFLICT EXCEPTION) should be used anytime a user provides data that can be fixed.
// This is more descriptive than a 400 (USER ERROR) error, so I created this custom exception class.
@ResponseStatus(value = HttpStatus.CONFLICT)
public class ConflictException extends RuntimeException {
    public ConflictException(String message) {
        super(message);
    }
}
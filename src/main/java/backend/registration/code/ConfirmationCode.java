package backend.registration.code;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity // Creates the confirmation_code table
public class ConfirmationCode {
    @Id // Generates a unique id for each confirmation code
    @SequenceGenerator(name = "confirmation_code_sequence", sequenceName = "confirmation_code_sequence", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "confirmation_code_sequence")
    private Long id;

    @Column(nullable = false) // marks this as a required column in our database
    private String itemToVerify;

    @Column(nullable = false)
    private String code;

    @Column(nullable = false)
    private LocalDateTime createdAt;

    @Column(nullable = false)
    private LocalDateTime expiresAt;

    private LocalDateTime confirmedAt; // this column can be null because the code may not be confirmed

    // Constructor used to create a new confirmation code
    public ConfirmationCode(String itemToVerify, String code, LocalDateTime createdAt, LocalDateTime expiresAt) {
        this.itemToVerify = itemToVerify;
        this.code = code;
        this.createdAt = createdAt;
        this.expiresAt = expiresAt;
    }

    // Default constructor needed to map request
    public ConfirmationCode() {}

    // Getter methods
    public String getItemToVerify() { return itemToVerify; }

    public String getCode() { return code; }

    public LocalDateTime getExpiresAt() { return expiresAt; }

    public LocalDateTime getConfirmedAt() { return confirmedAt; }
}

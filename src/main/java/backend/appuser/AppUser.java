package backend.appuser;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import java.util.Collection;
import java.util.Collections;

// JPA (Java Persistence API) specifies ORM (Object-Relation Mapping), in other words, how data is mapped to our
// SQL database. The ORM built into Spring Boot will handle the mapping from Java objects to columns in our database.
// Annotating the class with @Entity says that this class will create and map to a table in our database.
@Entity
// This class implements UserDetails. The abstract class, UserDetails, provides security that I can implement in the
// future. However most of the methods I overrode, as you will see, do not secure the server.
public class AppUser implements UserDetails {
    // Define the primary key and generate it automatically. Unique ID generated like 1,2,3,4,...
    @Id
    @SequenceGenerator(name = "user_sequence", sequenceName = "user_sequence", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "user_sequence")
    private Long id;
    private String name; // these fields should not be final as implementation may be added allowing changes
    private String email;
    private String phone;
    private String username;
    private String password;
    @Enumerated(EnumType.STRING) // Can be either ADMIN or USER. Currently, everyone is a USER.
    private AppUserRole appUserRole;

    // Constructor to make new AppUser objects. A POST request will map into a new object using this constructor.
    public AppUser(String name, String email, String phone, String username, String password, AppUserRole appUserRole) {
        this.name = name;
        this.email = email;
        this.phone = phone;
        this.username = username;
        this.password = password;
        this.appUserRole = appUserRole;
    }

    // Default constructor
    public AppUser() {}

    // The following function overrides are necessary to bring in Spring Security (required by implementing UserDetails)
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() { // returns permissions of current user
        SimpleGrantedAuthority authority = new SimpleGrantedAuthority(appUserRole.name()); // see if user is ADMIN or USER
        return Collections.singletonList(authority); // returns a collection of the user's authority
    }

    @Override
    public boolean isAccountNonExpired() {
        return true; // for now, I won't include functionality to detect if a signed in user's session has expired
    }

    @Override
    public boolean isAccountNonLocked() {
        return true; // this is commonly used when user's has unverified data. However, users are required to verify
        // their data upon account creation. Users accounts are also commonly locked when there are too many login attempts.
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true; // this is used when a password expires and needs changed. Companies usually require this to happen
        // every few months. I will not include this functionality.
    }

    @Override
    public boolean isEnabled() {
        return true; // In any server this should be true. This is an administrative property in which admins
        // disable users individually.
    }

    // Getters
    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }

    public String getPhone() {
        return phone;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    // Setters
    public void setPassword(String password) {
        this.password = password;
    }

    public void setAppUserRole(AppUserRole role) { this.appUserRole = role; }
}

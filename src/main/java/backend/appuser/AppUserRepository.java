package backend.appuser;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository // This annotation serves as a comment. Tells other programmers this layer is used to talk with the database.
// Extending JpaRepository gives us a lot of CRUD methods. We tell this class that this layer will transact AppUsers
// with an id of type Long.
public interface AppUserRepository extends JpaRepository<AppUser, Long> {
    // Queries the database to the get the user matching the provided email.
    @Query("SELECT u FROM AppUser u WHERE u.email = ?1") // This is SQL command. It reads like English. The ?1 refers
    // to the 1st argument in the findByEmail() function.
    public Optional<AppUser> findByEmail(String email); // Optional<> is used when a method may return nothing, but also,
    // in this case, could return an AppUser object

    // Returns a user that matches a given username
    @Query("SELECT u FROM AppUser u WHERE u.username = ?1")
    public Optional<AppUser> findByUsername(String username);

    // Returns a user that matches a given phone number
    @Query("SELECT u FROM AppUser u WHERE u.phone = ?1")
    public Optional<AppUser> findByPhone(String phone);

}

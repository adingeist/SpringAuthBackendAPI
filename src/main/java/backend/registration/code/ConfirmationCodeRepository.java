package backend.registration.code;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.Optional;

@Repository // Creates a repository with all the methods provided by the JpaRepository class
public interface ConfirmationCodeRepository extends JpaRepository<ConfirmationCode, Long> {
    // Returns nothing or a found ConfirmationCode that matches the given email/phone number
    public Optional<ConfirmationCode> findByItemToVerify(String itemToVerify);

    // @Transactional and @Modifying together says this method will modify the database
    @Transactional
    @Modifying
    @Query("DELETE FROM ConfirmationCode c WHERE c.itemToVerify = ?1")  // delete confirmation codes that match the item to verify
    public int deleteItems(String itemToVerify);

    @Transactional
    @Modifying
    @Query("UPDATE ConfirmationCode c SET c.confirmedAt = ?1 WHERE c.itemToVerify = ?2") // confirms the item
    public int updateConfirmedAt(LocalDateTime confirmedAt, String itemToVerify);
}

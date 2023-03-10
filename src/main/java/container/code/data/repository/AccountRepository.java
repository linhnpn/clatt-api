package container.code.data.repository;

import container.code.data.dto.ResponseObject;
import container.code.data.entity.Account;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface AccountRepository extends JpaRepository<Account, Integer> {
//    @Query("SELECT t FROM Account t")
    List<Account> findAll();

//    @Query("SELECT t FROM Account t WHERE t.id = ?1")
    Optional<Account> findById(Integer Id);

//    @Query("SELECT t FROM Account t WHERE t.role = ?1")
    List<Account> findByRole(String role);

//    Optional<Account> findByEmail(String email);
    Account findByEmail(String email);

    @Query("SELECT t FROM Account t WHERE t.username = ?1 and t.isLocked = FALSE")
    Account findByUsername(String username);

    @Query("SELECT t.email FROM Account t WHERE t.username = ?1")
    String findEmailByUsername(String username);

    @Query("SELECT t.username FROM Account t WHERE t.email = ?1")
    String findUsernameByEmail(String email);

    Account findByEmailAndPassword(String Email, String password);
    @Modifying
    @Transactional
    @Query(value = "Update Account t SET t.isLocked = TRUE WHERE t.id = :account_id")
    void banAccount(@Param("account_id") Integer id);

    @Query("SELECT t FROM Account t WHERE t.id NOT IN " +
            "(SELECT f.id FROM BookingOrder f WHERE YEAR(f.workDate) = YEAR(?1) and MONTH(f.workDate) = MONTH(?1) and DAY(f.workDate) = DAY(?1))")
    List<Account> getAccountFromOrderWithDate(LocalDateTime date);
}

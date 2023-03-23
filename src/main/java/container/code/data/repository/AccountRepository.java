package container.code.data.repository;

import container.code.data.entity.Account;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.Optional;

public interface AccountRepository extends JpaRepository<Account, Integer> {

    @Query("SELECT t FROM Account t")
    List<Account> findAll();

    @Query("SELECT t FROM Account t WHERE t.id = ?1 and t.isLocked = FALSE")
    Optional<Account> findById(Integer Id);

    @Query("SELECT t FROM Account t WHERE t.role = ?1 and t.isLocked = FALSE")
    List<Account> findByRole(String role);

    @Query("SELECT t FROM Account t WHERE t.email = ?1 and t.isLocked = FALSE")
    Account findByEmail(String email);

    @Query("SELECT t FROM Account t LEFT JOIN t.employeeJobs LEFT JOIN t.bookedOrders LEFT JOIN t.bookingOrders LEFT JOIN t.address ad LEFT JOIN ad.district di LEFT JOIN di.province WHERE t.isLocked = FALSE and t.username = :username")
    Account findByUsername(@Param("username") String username);

    @Query(value = "SELECT t.id as id, t.role as role, t.amount as amount, t.username as username," +
            " t.fullname as fullname, t.dateOfBirth as date_of_birth, " +
            " t.gender as gender, t.phone as phone, t.email as email," +
            " t.bio as bio, t.profilePicture as profilePicture, t.fcmToken" +
            " as fcmToken, ad.id as address_id, ad.description as address_des" +
            ", di.name as districtName, pro.name as province " +
            " FROM Account t INNER JOIN t.address ad INNER JOIN t.address.district di" +
            " INNER JOIN t.address.district.province pro WHERE t.isLocked = FALSE", nativeQuery = false)
    Map<String, Object> findByUsernameId(@Param("username") String username);

    @Modifying
    @Transactional
    @Query(value = "Update Account t SET t.isLocked = TRUE WHERE t.id = :account_id")
    void banAccount(@Param("account_id") Integer id);

    @Modifying
    @Transactional
    @Query(value = "Update Account t SET t.fcmToken = :fcmToken WHERE t.id = :account_id")
    void updateFcmToken(@Param("account_id") Integer id, @Param("fcmToken") String fcmToken);

    @Query("SELECT t FROM Account t WHERE t.id NOT IN " +
            "(SELECT f.id FROM BookingOrder f WHERE YEAR(f.workDate) = YEAR(?1) and MONTH(f.workDate) = MONTH(?1) and DAY(f.workDate) = DAY(?1))")
    List<Account> getAccountFromOrderWithDate(LocalDateTime date);

    @Query("SELECT count(t.id) FROM Account t WHERE t.role = :role")
    Integer getTotalAccount(@Param("role") String role);
}

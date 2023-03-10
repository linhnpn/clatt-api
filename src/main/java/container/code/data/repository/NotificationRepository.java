package container.code.data.repository;

import container.code.data.entity.Notification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.List;
import java.util.Optional;

public interface NotificationRepository extends JpaRepository<Notification, Integer> {
    @Query("SELECT t FROM Notification t WHERE t.id = ?1 and t.account.id = ?2")
    Optional<Notification> findByIdAndAccountId(Integer id, Integer accountId);

    @Query("SELECT t FROM Notification t WHERE t.account.id = ?1")
    List<Notification> findByAccountId(Integer accountId);
}

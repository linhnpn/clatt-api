package container.code.data.repository;

import container.code.data.entity.NotificationStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;

public interface NotificationStatusRepository extends JpaRepository<NotificationStatus, Integer> {
}

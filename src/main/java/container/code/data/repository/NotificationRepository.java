package container.code.data.repository;

import container.code.data.entity.Notification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;

public interface NotificationRepository extends JpaRepository<Notification, Integer> {
}

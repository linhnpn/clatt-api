package container.code.function.notification.service;

import container.code.data.dto.ResponseObject;
import org.springframework.http.ResponseEntity;

public interface NotificationService {
    ResponseEntity<ResponseObject> getNotificationDetail(Integer accountId, Integer notificationId);

    ResponseEntity<ResponseObject> getAllMyNotificationsById(Integer accountId);

    boolean createNotification(Integer userId, String status, String detail);

    boolean updateNotification(Integer id,String status);
}

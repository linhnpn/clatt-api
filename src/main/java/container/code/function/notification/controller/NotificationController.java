package container.code.function.notification.controller;

import container.code.data.dto.ResponseObject;
import container.code.function.notification.service.NotificationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin(origins = "https://cleaning-house-service.vercel.app", allowCredentials = "true")
@RequestMapping(value = "/notification")
public class NotificationController {

    @Autowired
    private NotificationService notificationService;

    @GetMapping("/getNotification")
    public ResponseEntity<ResponseObject> getNotificationDetail(
            @RequestParam(name = "account_id") Integer accountId,
            @RequestParam(name = "notification_id", required = false) Integer notificationId) {
        return notificationService.getNotificationDetail(accountId, notificationId);
    }

    @GetMapping("/getAllNotifications")
    public ResponseEntity<ResponseObject> getAllNotifications(
            @RequestParam(name = "account_id") Integer accountId) {
        return notificationService.getAllMyNotificationsById(accountId);
    }
}

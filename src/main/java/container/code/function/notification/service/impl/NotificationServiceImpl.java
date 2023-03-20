package container.code.function.notification.service.impl;

import container.code.data.dto.ResponseObject;
import container.code.data.entity.Account;
import container.code.data.entity.Notification;
import container.code.data.repository.AccountRepository;
import container.code.data.repository.NotificationRepository;
import container.code.function.notification.service.NotificationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.webjars.NotFoundException;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class NotificationServiceImpl implements NotificationService {

    @Autowired
    private NotificationRepository notificationRepository;
    @Autowired
    private AccountRepository accountRepository;

    @Override
    public ResponseEntity<ResponseObject> getNotificationDetail(Integer accountId, Integer notificationId) {
        Optional<Notification> notification = notificationRepository.findByIdAndAccountId(notificationId, accountId);

        if (notification.isPresent())
            return ResponseEntity.status(HttpStatus.FOUND).body(new ResponseObject(HttpStatus.FOUND.toString(), null, notification));
        else
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ResponseObject(HttpStatus.NOT_FOUND.toString(), "Can not find notification", null));
    }

    @Override
    public ResponseEntity<ResponseObject> getAllMyNotificationsById(Integer accountId) {
        List<Notification> listNotification = notificationRepository.findByAccountId(accountId);

        if (!listNotification.isEmpty())
            return ResponseEntity.status(HttpStatus.OK).body(new ResponseObject(HttpStatus.OK.toString(), null, listNotification));
        else
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ResponseObject(HttpStatus.NOT_FOUND.toString(), "Can not find notification", null));
    }

    @Override
    public boolean createNotification(Integer userId, String status, String detail, LocalDateTime timestamp) {
        Notification notification = new Notification();
        notification.setStatus(status);
        notification.setDetail(detail);
        notification.setTimestamp(timestamp);
        try {
            Account account = findAccount(userId);
            List<Notification> list = account.getNotifications();
            list.add(notification);
            notification.setAccount(account);
            account.setNotifications(list);
            notificationRepository.save(notification);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    private Account findAccount(Integer id) {
        Account existAccount = accountRepository.findById(id).orElseThrow(() -> new NotFoundException("Feedback not found"));
        return existAccount;
    }

    private Notification findNoti(Integer id) {
        Notification existNoti = notificationRepository.findById(id).orElseThrow(() -> new NotFoundException("Feedback not found"));
        return existNoti;
    }

    @Override
    public boolean updateNotification(Integer id, String status) {
        try {
            Notification notification = findNoti(id);
            if (notification.getId() != null) {
                notification.setStatus(status);
                notificationRepository.save(notification);
                return true;
            } else {
                return false;
            }
        } catch (Exception e) {
        }
        return false;
    }
}

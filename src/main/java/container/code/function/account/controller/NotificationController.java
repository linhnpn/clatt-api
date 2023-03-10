package container.code.function.account.controller;

import container.code.data.dto.NotificationRequestDto;
import container.code.data.dto.SubscriptionRequestDto;
import container.code.function.account.service.notification.FCMService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping("/notification")
@RestController
public class NotificationController {
    @Autowired
    private FCMService fcmService;
    @PostMapping("/subscribe")
    public void subscribeToTopic(@RequestBody SubscriptionRequestDto subscriptionRequestDto) {
        fcmService.subscribeToTopic(subscriptionRequestDto);
    }

    @PostMapping("/unsubscribe")
    public void unsubscribeFromTopic(SubscriptionRequestDto subscriptionRequestDto) {
        fcmService.unsubscribeFromTopic(subscriptionRequestDto);
    }
    @PostMapping("/topic")
    public String sendPnsToTopic(@RequestBody NotificationRequestDto notificationRequestDto) {
        return fcmService.sendPnsToTopic(notificationRequestDto);
    }
}

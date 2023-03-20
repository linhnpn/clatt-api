package container.code.function.account.controller;

import container.code.data.dto.NotificationRequestDto;
import container.code.data.dto.SubscriptionRequestDto;
import container.code.function.account.service.notification.FCMService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RequestMapping("/notification")
@RestController
@CrossOrigin(origins = {"http://localhost:3030", "https://clatt-api.monoinfinity.net",
        "https://cleaning-house-service.vercel.app", "http://localhost:8080"}, allowCredentials = "true")
public class PushNotificationController {
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

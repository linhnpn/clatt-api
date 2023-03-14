package container.code.function.account.service.notification;

import com.google.auth.oauth2.GoogleCredentials;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import com.google.firebase.messaging.FirebaseMessaging;
import com.google.firebase.messaging.FirebaseMessagingException;
import com.google.firebase.messaging.Message;
import com.google.firebase.messaging.Notification;
import container.code.data.dto.NotificationRequestDto;
import container.code.data.dto.SubscriptionRequestDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.io.IOException;
import java.util.concurrent.ExecutionException;

@Slf4j
@Service
public class FCMService {
    @Value("${firebase.credentials.path}")
    private String firebaseConfig;
    private FirebaseApp firebaseApp;

    @PostConstruct
    private void initialize() {
        try {
            FirebaseOptions options = new FirebaseOptions.Builder()

                    .setCredentials(GoogleCredentials.fromStream(new ClassPathResource(firebaseConfig).getInputStream())).build();

            if (FirebaseApp.getApps().isEmpty()) {
                this.firebaseApp = FirebaseApp.initializeApp(options);
            } else {
                this.firebaseApp = FirebaseApp.getInstance();
            }
        } catch (IOException e) {
            log.error("Create FirebaseApp Error", e);
        }
    }

    public void subscribeToTopic(SubscriptionRequestDto subscriptionRequestDto) {
        try {
            FirebaseMessaging.getInstance(firebaseApp).subscribeToTopic(subscriptionRequestDto.getTokens(),
                    subscriptionRequestDto.getTopicName());
        } catch (FirebaseMessagingException e) {
            log.error("Firebase subscribe to topic fail", e);
        }
    }

    public void unsubscribeFromTopic(SubscriptionRequestDto subscriptionRequestDto) {
        try {
            FirebaseMessaging.getInstance(firebaseApp).unsubscribeFromTopic(subscriptionRequestDto.getTokens(),
                    subscriptionRequestDto.getTopicName());
        } catch (FirebaseMessagingException e) {
            log.error("Firebase unsubscribe from topic fail", e);
        }
    }

    public String sendPnsToTopic(NotificationRequestDto notificationRequestDto) {
        Message.Builder builder = Message.builder();
        builder.setToken(notificationRequestDto.getTarget());
        builder.setNotification(Notification.builder().setTitle(notificationRequestDto.getTitle()).setBody(notificationRequestDto.getBody()).build());
        builder.putData("content", notificationRequestDto.getTitle());
        builder.putData("body", notificationRequestDto.getBody());
        Message message = builder
                .build();

        try {

            String res = FirebaseMessaging.getInstance(firebaseApp).sendAsync(message).get();
            log.info("Res = {}", res);
            log.info(notificationRequestDto.toString());
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        return null;
    }
}
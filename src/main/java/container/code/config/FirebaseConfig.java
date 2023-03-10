package container.code.config;

import com.google.auth.oauth2.GoogleCredentials;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import com.google.firebase.auth.FirebaseAuth;
import container.code.ClattAppsApplication;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.Resource;

import javax.annotation.PostConstruct;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Objects;

@Configuration
public class FirebaseConfig {
    @PostConstruct
    public void initializeFirebaseApp() throws IOException {
        ClassLoader classLoader = ClattAppsApplication.class.getClassLoader();
        File file = new File(Objects.requireNonNull(classLoader.getResource("serviceAccountKey.json")).getFile());
        FileInputStream serviceAccount = new FileInputStream(file.getAbsolutePath());

        FirebaseOptions options = FirebaseOptions.builder()
                .setCredentials(GoogleCredentials.fromStream(serviceAccount))
                .setServiceAccountId("firebase-adminsdk-6dzwy@cleaninghouseservice-23aa3.iam.gserviceaccount.com")
                .build();
        FirebaseApp.initializeApp(options);
    }
}

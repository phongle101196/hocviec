package com.demo.notification.config;

import com.google.auth.oauth2.GoogleCredentials;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseException;
import com.google.firebase.FirebaseOptions;
import com.google.firebase.messaging.FirebaseMessaging;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;

import java.io.IOException;
import java.io.InputStream;

@Configuration
public class FirebaseConfig {

    private static final Logger logger = LoggerFactory.getLogger(FirebaseConfig.class);

    @Value("${app.firebase-configuration-file}")
    private String firebaseConfigPath;

    @Bean
    public FirebaseMessaging firebaseMessaging() {
        try {
            // Đọc file từ classpath
            InputStream serviceAccount = new ClassPathResource(firebaseConfigPath).getInputStream();

            // Tạo FirebaseOptions
            FirebaseOptions options = FirebaseOptions.builder()
                    .setCredentials(GoogleCredentials.fromStream(serviceAccount))
                    .build();

            // Khởi tạo FirebaseApp
            FirebaseApp app;
            if (FirebaseApp.getApps().isEmpty()) {
                app = FirebaseApp.initializeApp(options);
                logger.info("FirebaseApp initialized: {}", app.getName());
            } else {
                app = FirebaseApp.getInstance();
                logger.info("Using existing FirebaseApp: {}", app.getName());
            }

            return FirebaseMessaging.getInstance(app);

        } catch (IOException e) {
            logger.error("Không thể đọc file cấu hình Firebase: {}", e.getMessage(), e);
            throw new RuntimeException("Lỗi cấu hình Firebase", e);
        }
    }
}

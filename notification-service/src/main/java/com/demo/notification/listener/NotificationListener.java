package com.demo.notification.listener;

import com.demo.kafka.dto.HttpServiceDto;
import com.demo.notification.service.FcmTokenService;
import com.demo.notification.service.FirebaseService;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import com.demo.notification.service.EmailService;
import com.demo.notification.service.TelegramService;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

import java.util.Map;

@Component
@RequiredArgsConstructor
public class NotificationListener {

    private static final Logger logger = LoggerFactory.getLogger(NotificationListener.class);

    private final EmailService emailService;
    private final TelegramService telegramService;
    private final FirebaseService firebaseService;
    private final FcmTokenService fcmTokenService;

    @KafkaListener(topics = "employee-topic", groupId = "notification-group")
    public void handleEmployeeEvent(HttpServiceDto dto) {

        try {
            Map<String, Object> body = dto.getBody();

            String notificationType = (String) body.getOrDefault("notificationType", "FIREBASE");
            String message = buildNotificationMessage(body);

            switch (notificationType.toUpperCase()) {
                case "EMAIL":
                    handleEmailNotification(body, message);
                    break;
                case "TELEGRAM":
                    handleTelegramNotification(body, message);
                    break;
                case "FIREBASE":
                    handleFirebaseNotification(body, message);
                    break;
                default:
                    logger.warn("Unsupported notification type: {}", notificationType);
            }

        } catch (Exception e) {
            logger.error("Error processing notification event: {}", e.getMessage(), e);
        }
    }

    private void handleEmailNotification(Map<String, Object> body, String message) {
        String email = (String) body.get("email");
        String name = (String) body.get("name");

        emailService.sendWelcomeEmail(email, name);
        logger.info("Sent welcome email to: {}", email);
    }

    private void handleTelegramNotification(Map<String, Object> body, String message) {
        telegramService.sendMessage(message);
        logger.info("Sent Telegram message: {}", message);
    }


    private String buildNotificationMessage(Map<String, Object> body) {
        String name = (String) body.get("name");
        return String.format("Xin chào *%s*! Chào mừng bạn đến với công ty DEMO", name);
    }

//    private void handleFirebaseNotification(Map<String, Object> body, String message) {
//        String deviceToken = (String) body.get("deviceToken");
//
//        if (deviceToken == null || deviceToken.isEmpty()) {
//            logger.error("Missing device token for Firebase notification.");
//            return;
//        }
//
//        String title = "Chào mừng bạn đến với DEMO!";
//        firebaseService.sendPushNotification(title, message, deviceToken);
//        logger.info("Mock Firebase push sent to token: {}", deviceToken);
//    }

    private void handleFirebaseNotification(Map<String, Object> body, String message) {
        String email = (String) body.get("email");
        String token = fcmTokenService.getTokenByEmail(email);

        if (token == null || token.isEmpty()) {
            logger.warn("No FCM token found for email: {}", email);
            return;
        }

        firebaseService.sendPushNotification(token, "Chào mừng!", message);
        logger.info("Sent Firebase notification to {} (token: ...{})", email, token.substring(0, 10));
    }

}

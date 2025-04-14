package com.demo.notification.service;

import com.google.auth.oauth2.GoogleCredentials;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import com.google.firebase.messaging.*;
import jakarta.annotation.PostConstruct;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.io.InputStream;

@Slf4j
@Service
public class FirebaseService {

    @PostConstruct
    public void initializeFirebase() {
        try {
            InputStream serviceAccount =
                    getClass().getClassLoader().getResourceAsStream("firebase-service-account.json");

            if (serviceAccount == null) {
                throw new IllegalStateException("firebase-service-account.json not found!");
            }

            FirebaseOptions options = FirebaseOptions.builder()
                    .setCredentials(GoogleCredentials.fromStream(serviceAccount))
                    .build();

            if (FirebaseApp.getApps().isEmpty()) {
                FirebaseApp.initializeApp(options);
                log.info("✅ Firebase initialized!");
            }

        } catch (IOException e) {
            log.error("Failed to initialize Firebase", e);
        }
    }

    public void sendPushNotification(String fcmToken, String title, String body) {
        Notification notification = Notification.builder()
                .setTitle(title)
                .setBody(body)
                .build();

        Message message = Message.builder()
                .setToken(fcmToken)
                .setNotification(notification)
                .build();

        try {
            String response = FirebaseMessaging.getInstance().send(message);
            log.info("Notification sent successfully! Response: {}", response);
        } catch (FirebaseMessagingException e) {
            log.error("Failed to send Firebase notification", e);
        }
    }
}








































//package com.demo.notification.service;
//
//import com.google.firebase.messaging.FirebaseMessaging;
//import com.google.firebase.messaging.FirebaseMessagingException;
//import com.google.firebase.messaging.Message;
//import com.google.firebase.messaging.Notification;
//import lombok.RequiredArgsConstructor;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Service;
//
//@Service
//@RequiredArgsConstructor
//public class FirebaseService {
//    private final FirebaseMessaging firebaseMessaging;
//
//    public void sendPushNotification(String title, String message, String deviceToken) {
//        System.out.println("🔥 [MOCK] Firebase Push Notification Sent:");
//        System.out.println("    ➤ To DeviceToken : " + deviceToken);
//        System.out.println("    ➤ Title          : " + title);
//        System.out.println("    ➤ Message        : " + message);
//    }
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
////    public void sendNotifiactionFirebase(String deviceToken, String employeeName) throws FirebaseMessagingException {
////        String title = "Chào mừng thành viên mới !!!";
////        String body  = buildNotificationMessage(employeeName);
////
////        Message message = Message.builder()
////                .setToken(deviceToken)
////                .setNotification(Notification.builder()
////                        .setTitle(title)
////                        .setBody(body)
////                        .build())
////                .build();
////
////        firebaseMessaging.send(message);
////    }
////    private String buildNotificationMessage(String name) {
////        return String.format("Xin chào %s, tài khoản của bạn đã được tạo thành công!", name);
////    }
//}





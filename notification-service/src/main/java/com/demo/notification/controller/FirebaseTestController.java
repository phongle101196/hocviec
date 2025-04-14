package com.demo.notification.controller;

import com.demo.notification.service.FirebaseService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping("/api/test")
@RequiredArgsConstructor
public class FirebaseTestController {
    private final FirebaseService firebaseService;

    @PostMapping("/send")
    public ResponseEntity<String> testSend(@RequestBody Map<String, String> body) {
        String token = body.get("token");
        String title = "🔔 Xin chào từ server!";
        String content = "Bạn vừa nhận thông báo test FCM thành công 💥";

        firebaseService.sendPushNotification(token, title, content);

        return ResponseEntity.ok("Notification sent!");
    }
}

package com.demo.notification.controller;

import com.demo.notification.dto.FcmTokenRequest;
import com.demo.notification.service.FcmTokenService;
import com.demo.notification.service.FirebaseService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class FcmTokenController {
    private final FcmTokenService fcmTokenService;
    private final FirebaseService firebaseService;

    @PostMapping("/save-token")
    public ResponseEntity<?> saveToken(@RequestBody FcmTokenRequest request) {
        fcmTokenService.save(request.getEmail(), request.getFcmToken());

        // Delay gửi sau 3s để đảm bảo FE đã vào welcome.html
        Executors.newSingleThreadScheduledExecutor().schedule(() -> {
            firebaseService.sendPushNotification(
                    request.getFcmToken(),
                    "Welcome!",
                    "Xin chào, bạn đã đăng nhập thành công!"
            );
        }, 1, TimeUnit.SECONDS);

        return ResponseEntity.ok("Token saved!");
    }
}

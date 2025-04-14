package com.demo.notification.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Slf4j
@Service
public class FcmTokenService {
    private final Map<String, String> tokenStore = new ConcurrentHashMap<>();

    public void save(String email, String fcmToken) {
        tokenStore.put(email, fcmToken);
        log.info("Đã lưu FCM token cho email {}: {}", email, fcmToken);
    }

    public String getTokenByEmail(String email) {
        return tokenStore.get(email);
    }
}

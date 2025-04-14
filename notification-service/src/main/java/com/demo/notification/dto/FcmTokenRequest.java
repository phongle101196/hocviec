package com.demo.notification.dto;

import lombok.Data;

@Data
public class FcmTokenRequest {
    private String email;
    private String fcmToken;
}

package com.demo.notification.service;

import com.demo.notification.listener.NotificationListener;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class EmailService {

    private final JavaMailSender mailSender;

    private static final Logger logger = LoggerFactory.getLogger(EmailService.class);

    public void sendWelcomeEmail(String email, String name) {
        SimpleMailMessage mailMessage = new SimpleMailMessage();
        mailMessage.setTo(email);
        mailMessage.setSubject("Welcome to Our Company!");
        mailMessage.setText("Dear " + name + ",\n\nYour account has been created successfully!");
        mailSender.send(mailMessage);

        logger.info("Sending email to: {}", email);
        logger.info("Recipient name: {}", name);

    }
}

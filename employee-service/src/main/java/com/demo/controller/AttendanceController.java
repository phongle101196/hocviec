package com.demo.controller;

import com.demo.kafka.dto.HttpServiceDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/employees")
public class AttendanceController {

    @Autowired
    private KafkaTemplate<String, HttpServiceDto> kafkaTemplate;
    @PostMapping("/{employeeId}/check-in")
    public ResponseEntity<?> checkIn(@PathVariable Long employeeId) {

        Map<String, String> headers = new HashMap<>();
        headers.put("Content-Type", "application/json");
        headers.put("Authorization", "112121212");

        Map<String, Object> body = new HashMap<>();
        body.put("employeeId", employeeId);
        body.put("checkInTime", LocalDateTime.now().toString());

        HttpServiceDto httpServiceDto = new HttpServiceDto(
                "/api/employees",  // URL đích
                "POST",            // HTTP Method
                body,              // Body data
                headers            // Headers
        );

        // Gửi sự kiện đến Kafka
        kafkaTemplate.send("attendance-topic", httpServiceDto);

        return ResponseEntity.ok("Nhân viên " + employeeId + " đã điểm danh thành công!");
    }
}

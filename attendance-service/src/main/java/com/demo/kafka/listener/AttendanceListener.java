package com.demo.kafka.listener;

import com.demo.attendance.dto.EmployeeAttendanceDto;
import com.demo.attendance.service.AttendanceService;
import com.demo.kafka.dto.HttpServiceDto;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
public class AttendanceListener {
    private static final Logger logger = LoggerFactory.getLogger(AttendanceListener.class);

    @Autowired
    private AttendanceService attendanceService;

    private final ObjectMapper objectMapper = new ObjectMapper();

    @KafkaListener(topics = "attendance-topic", groupId = "attendance-group",
            containerFactory = "kafkaListenerContainerFactory")
    public void listen(HttpServiceDto message) {
        logger.info("Received Kafka message: {}", message);

        try {
            if (message.getBody() == null) {
                logger.warn("Message body is null, skipping...");
                return;
            }

            EmployeeAttendanceDto dto = toDto(message);
            attendanceService.recordAttendance(dto);

        } catch (Exception e) {
            logger.error("Error processing attendance message", e);
        }
    }

    private EmployeeAttendanceDto toDto(HttpServiceDto message) {
        return objectMapper.convertValue(message.getBody(), EmployeeAttendanceDto.class);
    }
}

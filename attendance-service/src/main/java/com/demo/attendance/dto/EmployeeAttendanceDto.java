package com.demo.attendance.dto;

import lombok.Data;

@Data
public class EmployeeAttendanceDto {
    private Long employeeId;
    private String checkInTime;
}

package com.demo.attendance.service;

import com.demo.attendance.dto.EmployeeAttendanceDto;
import com.demo.attendance.entity.AttendanceRecord;

import java.util.List;
import java.util.Map;
import java.util.Optional;

public interface IAttendanceService {
    void recordAttendance(EmployeeAttendanceDto dto);
    List<AttendanceRecord> getAllAttendance();

    Optional<AttendanceRecord> getAttendanceById(Long id);
    List<AttendanceRecord> getAttendanceByEmployeeId(Long employeeId);
    void deleteAttendance(Long id);

}

package com.demo.attendance.service;

import com.demo.attendance.dto.EmployeeAttendanceDto;
import com.demo.attendance.entity.AttendanceRecord;
import com.demo.attendance.repository.AttendanceRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AttendanceService implements IAttendanceService {

    private final AttendanceRepository attendanceRepository;

    @Override
    public void recordAttendance(EmployeeAttendanceDto dto) {

        if (dto.getEmployeeId() == null || dto.getCheckInTime() == null) {
            throw new IllegalArgumentException("Invalid attendance data");
        }

        AttendanceRecord record = new AttendanceRecord();
        record.setEmployeeId(dto.getEmployeeId());
        record.setCheckInTime(LocalDateTime.parse(dto.getCheckInTime()));

        attendanceRepository.save(record);
    }

    @Override
    public List<AttendanceRecord> getAllAttendance() {
        return attendanceRepository.findAll();
    }

    @Override
    public Optional<AttendanceRecord> getAttendanceById(Long id) {
            return attendanceRepository.findById(id);
    }

    @Override
    public List<AttendanceRecord> getAttendanceByEmployeeId(Long employeeId) {
        return attendanceRepository.findByEmployeeId(employeeId);
    }

    @Override
    public void deleteAttendance(Long id) {
        attendanceRepository.deleteById(id);
    }
}

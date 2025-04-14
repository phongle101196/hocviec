package com.demo.service;

import com.demo.dto.CreateEmployeeDto;
import com.demo.dto.EmployeeDto;
import com.demo.dto.UpdateEmployeeDto;
import com.demo.entity.Employee;
import org.springframework.data.domain.Page;

import java.util.List;
import java.util.Map;

public interface IEmployeeService {
    CreateEmployeeDto createEmployee(Map<String, String> requestBody);
    Employee getEmployeeById(Long id);
    List<Employee> getAllEmployees();
    UpdateEmployeeDto updateEmployee(Long id, Map<String, String> requestBody);
    void deleteEmployee(Long id);
}

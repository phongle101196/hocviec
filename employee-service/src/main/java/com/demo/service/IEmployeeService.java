package com.demo.service;

import com.demo.dto.CreateEmployeeDto;
import com.demo.dto.EmployeeDto;
import com.demo.dto.FilterCriteria;
import com.demo.dto.UpdateEmployeeDto;
import com.demo.entity.Employee;

import java.util.List;
import java.util.Map;

public interface IEmployeeService {
    EmployeeDto createEmployee(Map<String, String> requestBody);
    Employee getEmployeeById(Long id);
    List<Employee> getAllEmployees();
    EmployeeDto updateEmployee(Long id, Map<String, String> requestBody);
    void deleteEmployee(Long id);
    List<EmployeeDto> searchEmployees(List<FilterCriteria> filters);
}

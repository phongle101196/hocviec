package com.demo.service;

import com.demo.dto.CreateEmployeeDto;
import com.demo.dto.UpdateEmployeeDto;
import com.demo.entity.Employee;
import com.demo.exception.DuplicateResourceException;
import com.demo.exception.ResourceNotFoundException;
import com.demo.kafka.dto.HttpServiceDto;
import com.demo.repository.EmployeeRepository;
import com.demo.util.SpringUtils;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class EmployeeService implements IEmployeeService{

    private final EmployeeRepository employeeRepository;
    private final KafkaTemplate<String, HttpServiceDto> kafkaTemplate;
    private final ModelMapper modelMapper;


    @Override
    public CreateEmployeeDto createEmployee(Map<String, String> requestBody) {

        String email = requestBody.get("email");
        if (employeeRepository.existsByEmail(email)){
            throw new DuplicateResourceException("Email already exists!");
        }

        Employee employee = new Employee();
        employee.setName(requestBody.get("name"));
        employee.setEmail(email);
        employee.setSalary(SpringUtils.convertToLong(requestBody.get("salary")));

        // lưu db
        Employee savedEmployee = employeeRepository.save(employee);

        Map<String, String> headers = new HashMap<>();
        headers.put("Content-Type", "application/json");
        headers.put("Authorization", "112121212");

        Map<String, Object> body = new HashMap<>();
        body.put("employeeId", savedEmployee.getId());
        body.put("name", savedEmployee.getName());
        body.put("email", savedEmployee.getEmail());
        body.put("notificationType", "FIREBASE");
        body.put("action", "CREATED");

        HttpServiceDto httpServiceDto = new HttpServiceDto(
                "/api/employees",  // url đích
                "POST",            // http
                body,              // data
                headers            // phần headers
        );

        // Gửi sự kiện đến Kafka
        kafkaTemplate.send("employee-topic", httpServiceDto);

        return modelMapper.map(savedEmployee, CreateEmployeeDto.class);
    }


    @Override
    public Employee getEmployeeById(Long id) {
        return employeeRepository.findById(id).orElseThrow(()
                -> new RuntimeException("Employee not found"));
    }

    @Override
    public List<Employee> getAllEmployees() {
        return employeeRepository.findAll();
    }

    @Override
    public UpdateEmployeeDto updateEmployee(Long id, Map<String, String> requestBody) {

        // tìm employee để cập nhật
        Employee existingEmployee = employeeRepository.findById(id)
                .orElseThrow(() -> new  ResourceNotFoundException(
                        "Employee not found with id: " + id));

        String name = requestBody.get("name");
        Long salary = SpringUtils.convertToLong(requestBody.get("salary"));
        existingEmployee.setName(name);
        existingEmployee.setSalary(salary);

        // lưu db
        Employee updateEmployee = employeeRepository.save(existingEmployee);
        // map
        return modelMapper.map(updateEmployee, UpdateEmployeeDto.class);
    }

    @Override
    public void deleteEmployee(Long id) {
        Employee existsEmployee = employeeRepository.findById(id)
                        .orElseThrow(() -> new ResourceNotFoundException("Employee not found with id: " +id));
        employeeRepository.deleteById(id);
    }

}

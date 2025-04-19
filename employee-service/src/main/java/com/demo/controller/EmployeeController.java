package com.demo.controller;

import com.demo.dto.*;
import com.demo.entity.Employee;
import com.demo.exception.ResourceNotFoundException;
import com.demo.service.IEmployeeService;
import com.demo.validation.service.ValidationModule;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/employees")
@RequiredArgsConstructor
public class EmployeeController {

    private final IEmployeeService employeeService;
    private final ValidationModule validationModule;

    // CREATE
    @PostMapping
    public ResponseEntity<ApiResponse<EmployeeDto>> createEmployee(
            @RequestParam String className,
            @RequestBody Map<String, String> requestBody) {

        // kiểm tra lỗi đầu vào
        Map<String, List<String>> errors = validationModule.validate(className, requestBody);
        if (!errors.isEmpty()) {

            List<String> errorMessage = new ArrayList<>();

            errors.forEach((field, messages) -> {
                messages.forEach(message -> {
                    errorMessage.add("Field '" + field + "' " + message);
                });
            });

            return ResponseEntity.badRequest().body(ApiResponse.error(
                    HttpStatus.BAD_REQUEST.value(),
                    "Invalid input data!",
                    errorMessage));
        }

        // gọi service tạo mới employee
        EmployeeDto created = employeeService.createEmployee(requestBody);

        // trả về response thành công
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(ApiResponse.success(HttpStatus.CREATED, "Employee created successfully!", created));
    }

    @GetMapping("/{id}")
    public ResponseEntity<Employee> getEmployeeById(@PathVariable Long id) {
        return ResponseEntity.ok(employeeService.getEmployeeById(id));
    }

    @GetMapping
    public ResponseEntity<List<Employee>> getAllEmployees() {
        return ResponseEntity.ok(employeeService.getAllEmployees());
    }

    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse<EmployeeDto>> updateEmployee(
            @PathVariable Long id,
            @RequestParam String className,
            @RequestBody Map<String, String> requestBody) {
        // kiểm tra dl đầu vào
        Map<String, List<String>> errors = validationModule.validate(className, requestBody);
        if (!errors.isEmpty()) {

            List<String> errorMessage = new ArrayList<>();

            errors.forEach((field, messages) -> {
                messages.forEach(message -> {
                    errorMessage.add("Field '" + field + "' " + message);
                });
            });

            return ResponseEntity.badRequest().body(ApiResponse.error(
                    HttpStatus.BAD_REQUEST.value(),
                    "Invalid input data!",
                    errorMessage));
        }

        // gọi service update
        EmployeeDto updated = employeeService.updateEmployee(id, requestBody);

        return ResponseEntity.ok(ApiResponse.success(
                "Employee updated successfully!", updated));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<Void>> deleteEmployee(@PathVariable Long id) {
        try {
            employeeService.deleteEmployee(id);
            return ResponseEntity
                    .noContent()
                    .build();
        } catch (ResourceNotFoundException e) {
            return ResponseEntity
                    .status(HttpStatus.NOT_FOUND)
                    .body(ApiResponse.error(HttpStatus.NOT_FOUND.value(), e.getMessage()));
        } catch (Exception e) {
            return ResponseEntity
                    .status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(ApiResponse.error(HttpStatus.INTERNAL_SERVER_ERROR.value(), "An error occurred"));
        }
    }

    @PostMapping("/search")
    public ResponseEntity<ApiResponse<List<EmployeeDto>>> searchEmployees(@RequestBody List<FilterCriteria> filters) {
        List<EmployeeDto> employees = employeeService.searchEmployees(filters);
        return ResponseEntity.ok(new ApiResponse<>(HttpStatus.OK.value(),"Success", employees, null));
    }
}

package com.hrd.service;

import com.hrd.entity.Employee;
import org.apache.coyote.Response;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface EmployeeService {

    ResponseEntity<List<Employee>> getAllEmployees();
    ResponseEntity<Employee> addEmployee(Employee employee);
    ResponseEntity<Employee> getEmployeeById(Long id);
}

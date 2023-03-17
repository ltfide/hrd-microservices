package com.hrd.serviceimpl;

import com.hrd.entity.Employee;
import com.hrd.event.EmployeeEvent;
import com.hrd.repository.EmployeeRepository;
import com.hrd.service.EmployeeService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
public class EmployeeServiceImpl implements EmployeeService {

    @Autowired
    private EmployeeRepository employeeRepository;

    @Autowired
    KafkaTemplate<String, EmployeeEvent> kafkaTemplate;

    @Override
    public ResponseEntity<List<Employee>> getAllEmployees() {
        return ResponseEntity.ok(employeeRepository.findAll());
    }

    @Override
    public ResponseEntity<Employee> addEmployee(Employee employee) {
        kafkaTemplate.send("notificationTopic", new EmployeeEvent(employee.getEmail()));
        return ResponseEntity.ok(employeeRepository.save(employee));
    }

    @Override
    public ResponseEntity<Employee> getEmployeeById(Long id) {
        return ResponseEntity.ok(employeeRepository.findById(id).orElse(null));
    }


}

package com.hrd.serviceimpl;

import com.hrd.dto.EmployeeDto;
import com.hrd.entity.LeavePermit;
import com.hrd.repository.EmployeeRepository;
import com.hrd.repository.LeaveRepository;
import com.hrd.service.LeaveService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.HashMap;
import java.util.Map;

@Service
@Slf4j
public class LeaveServiceImpl implements LeaveService {

    @Autowired
    private LeaveRepository leaveRepository;

    @Autowired
    private EmployeeRepository employeeRepository;

    @Override
    public ResponseEntity<?> addLeavePermit(Long employeeId, LeavePermit request) {
        EmployeeDto employeeDto = employeeRepository.getEmployeeById(employeeId);

        if (employeeDto != null) {
            request.setEmployeeId(employeeDto.getId());
        }

        LeavePermit leavePermit = leaveRepository.save(request);
        return ResponseEntity.ok(mapToEmployee(leavePermit, employeeDto));
    }

    private Map<String, Object> mapToEmployee(LeavePermit leavePermit, EmployeeDto employeeDto) {
        Map<String, Object> response = new HashMap<>();
        response.put("id", leavePermit.getId());
        response.put("submission_name", leavePermit.getSubmissionName());
        response.put("type", leavePermit.getType());
        response.put("start_date", leavePermit.getStartDate());
        response.put("end_date", leavePermit.getEndDate());
        response.put("start_time", leavePermit.getStartTime());
        response.put("detail", leavePermit.getDetail());
        response.put("employee", employeeDto);
        return response;
    }
}

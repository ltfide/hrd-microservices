package com.hrd.service;

import com.hrd.entity.LeavePermit;
import org.springframework.http.ResponseEntity;

public interface LeaveService {

    ResponseEntity<?> addLeavePermit(Long employeeId, LeavePermit request);
}

package com.hrd.controller;

import com.hrd.entity.LeavePermit;
import com.hrd.service.LeaveService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/leave")
public class LeaveController {

    @Autowired
    private LeaveService leaveService;

    @PostMapping("/add/{employeeId}")
    public ResponseEntity<?> addLeavePermit(@PathVariable("employeeId") Long employeeId, @RequestBody LeavePermit leavePermit) {
        return leaveService.addLeavePermit(employeeId, leavePermit);
    }
}

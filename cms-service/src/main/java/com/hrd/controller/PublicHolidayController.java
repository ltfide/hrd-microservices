package com.hrd.controller;

import com.hrd.service.PublicHolidayService;
import com.hrd.implement.PublicHolidayImplement;
import com.hrd.model.request.PublicHolidayModelRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/v1/public-holiday")
@RequiredArgsConstructor
public class PublicHolidayController implements PublicHolidayImplement {

    @Autowired
    private final PublicHolidayService publicHolidayService;

    @Override
    @PostMapping("/insert")
    public ResponseEntity<Map<String, Object>> createPublicHoliday(@RequestBody PublicHolidayModelRequest request) {
        return publicHolidayService.createPublicHoliday(request);
    }

    @Override
    @PutMapping("/update/{id}")
    public ResponseEntity<Map<String, Object>> updatePublicHoliday(@PathVariable("id") Long id, @RequestBody PublicHolidayModelRequest request) {
        return publicHolidayService.updatePublicHoliday(id, request);
    }

    @Override
    @PostMapping("/getAllPublicHoliday")
    public ResponseEntity<Map<String, Object>> getAllPublicHoliday(@RequestBody Object params) {
        return publicHolidayService.getAllPublicHoliday(params);
    }

    @Override
    @GetMapping("/getPublicHoliday/{id}")
    public ResponseEntity<Map<String, Object>> getPublicHolidayById(@PathVariable("id") Long id) {
        return publicHolidayService.getPublicHolidayById(id);
    }

    @Override
    @PatchMapping("/deletePublicHoliday/{id}")
    public ResponseEntity<Map<String, Object>> deletePublicHoliday(@PathVariable("id") Long id) {
        return publicHolidayService.deletePublicHoliday(id);
    }
}

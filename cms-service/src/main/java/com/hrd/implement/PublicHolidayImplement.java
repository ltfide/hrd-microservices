package com.hrd.implement;

import com.hrd.model.request.PublicHolidayModelRequest;
import org.springframework.http.ResponseEntity;

import java.util.Map;

public interface PublicHolidayImplement {
    ResponseEntity<Map<String, Object>> createPublicHoliday(PublicHolidayModelRequest request);

    ResponseEntity<Map<String, Object>> updatePublicHoliday(Long id, PublicHolidayModelRequest request);

    ResponseEntity<Map<String, Object>> getAllPublicHoliday(Object params);

    ResponseEntity<Map<String, Object>> getPublicHolidayById(Long id);

    ResponseEntity<Map<String, Object>> deletePublicHoliday(Long id);
}

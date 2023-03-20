package com.hrd.payload;

import jakarta.validation.ConstraintViolation;
import lombok.*;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

@Getter
@Setter
public record ApiResponse(Long code, String status) {

    public Map<String, Object> setValidate(Set<? extends ConstraintViolation<?>> validate) {
        final Map<String, Object> validations = new HashMap<>();
        validate.forEach(constraintViolation -> {
            String key = constraintViolation.getPropertyPath().toString();
            Object val = constraintViolation.getMessage();
            validations.put(key, val);
        });
        Map<String, Object> response = new HashMap<>();
        response.put("code", this.code);
        response.put("status", this.status);
        response.put("message", validations);
        return response;
    }

    public Map<String, Object> setMessage(Object message) {
        Map<String, Object> response = new HashMap<>();
        response.put("code", this.code);
        response.put("status", this.status);
        response.put("message", message);
        return response;
    }

    public Map<String, Object> setEntity(Object entity) {
        Map<String, Object> response = new HashMap<>();
        response.put("code", this.code);
        response.put("status", this.status);
        response.put("data", entity);
        return response;
    }


}

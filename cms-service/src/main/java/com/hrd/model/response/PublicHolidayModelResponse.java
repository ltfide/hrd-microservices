package com.hrd.model.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class PublicHolidayModelResponse {
    private Long id;
    private LocalDate holidayDate;
    private String holidayName;
}

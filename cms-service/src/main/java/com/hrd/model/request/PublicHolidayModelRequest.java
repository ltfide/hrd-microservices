package com.hrd.model.request;

import lombok.*;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class PublicHolidayModelRequest {
    private LocalDate holidayDate;
    private String holidayName;
}

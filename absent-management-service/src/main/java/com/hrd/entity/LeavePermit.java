package com.hrd.entity;

import com.hrd.dto.EmployeeDto;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "leave_permit")
public class LeavePermit {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "submission_name", length = 100, nullable = false)
    private String submissionName;

    @Column(name = "employee_id")
    private Long employeeId;

    @Column(name = "type", nullable = false)
    @Enumerated(EnumType.STRING)
    private TypeLeave type;

    @Column(name = "start_date", nullable = false)
    @Temporal(TemporalType.DATE)
    private LocalDate startDate;

    @Column(name = "end_date", nullable = false)
    @Temporal(TemporalType.DATE)
    private LocalDate endDate;

    @Column(name = "start_time")
    @Temporal(TemporalType.TIME)
    private LocalTime startTime;

    @Column(name = "detail")
    private String detail;
}

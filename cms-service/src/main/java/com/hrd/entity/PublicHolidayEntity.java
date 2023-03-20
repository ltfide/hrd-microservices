package com.hrd.entity;

import com.hrd.util.CommonObjectCreatedDate;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;

import java.io.Serializable;
import java.time.LocalDate;

@Entity
@Table(name = "public_holidays")
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
@EqualsAndHashCode(callSuper = true)
public class PublicHolidayEntity extends CommonObjectCreatedDate implements Serializable {

    private static final Long serialVersionUID = 1L;

    @Id
    @SequenceGenerator(name = "publicHolidaySequence", sequenceName = "publicHoliday_seq", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "publicHolidaySequence")
    @Column(name = "id", nullable = false)
    private Long id;

    @Column(name = "holiday_date", nullable = false)
    @NotNull(message = "Please provide a holiday date")
    private LocalDate holidayDate;

    @Size(min = 3, max = 100, message = "name must be at least 3 and max 100 character")
    @Column(name = "holiday_name")
    @NotNull(message = "Please provide a holiday name")
    private String holidayName;

}

package com.hrd.entity;

import com.hrd.entity.enums.Gender;
import com.hrd.entity.enums.Religion;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.net.URL;
import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "employee")
public class Employee {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "full_name", length = 200, nullable = false)
    private String fullName;

    @Column(name = "place_birth", length = 100, nullable = false)
    private String placeBirth;

    @Column(name = "birth_date", nullable = false)
    private LocalDate birthDate;

    @Column(name = "religion", nullable = false)
    @Enumerated(EnumType.STRING)
    private Religion religion;

    @Column(name = "contact", length = 50, nullable = false)
    private String contact;

    @Column(name = "last_education", length = 100, nullable = false)
    private String lastEducation;

    @Column(name = "emergency_contact", length = 50, nullable = false)
    private String emergencyContact;

    @Column(name = "email", length = 50, nullable = false, unique = true)
    private String email;

    @Column(name = "join_date", nullable = false)
    private LocalDate joinDate;

    @Column(name = "ktp_address", length = 50, nullable = false)
    private String ktpAddress;

    @Column(name = "postal_code", length = 6, nullable = false)
    private String postalCode;

    @Column(name = "photo_ktp")
    private URL photoKtp;

    @Column(name = "gender", nullable = false)
    @Enumerated(EnumType.STRING)
    private Gender gender;
}

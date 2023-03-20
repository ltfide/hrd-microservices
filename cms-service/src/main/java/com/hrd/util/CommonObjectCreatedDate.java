package com.hrd.util;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.Column;
import javax.persistence.MappedSuperclass;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

@MappedSuperclass
@Getter
public class CommonObjectCreatedDate {

    @Setter
    @Column(name = "status", nullable = false)
    @NotNull(message = "Please provide a status")
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private Integer status;

    @Setter
    @Column(name = "created_by", nullable = false)
    @NotNull(message = "Please provide a created by")
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private Long createdBy;

    @Setter
    @Column(name = "updated_by", nullable = false)
    @NotNull(message = "Please provide a updated by")
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private Long updatedBy;

    @CreationTimestamp
    @Column(name = "created_date", nullable = false)
    @JsonFormat(pattern = GeneralConstants.Y_M_D_H_M_S, shape = JsonFormat.Shape.STRING, timezone = GeneralConstants.APPLICATION_TIME_ZONE)
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private LocalDateTime createdDate;

    @UpdateTimestamp
    @Column(name = "updated_date", nullable = false)
    @JsonFormat(pattern = GeneralConstants.Y_M_D_H_M_S, shape = JsonFormat.Shape.STRING, timezone = GeneralConstants.APPLICATION_TIME_ZONE)
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private LocalDateTime updatedDate;

}

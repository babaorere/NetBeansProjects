package com.isiweek.employee.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.constraints.Digits;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import java.math.BigDecimal;
import java.time.LocalDate;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class EmployeeDTO {

    private Long id;

    @NotNull
    private LocalDate dateOfHire;

    @NotNull
    @Digits(integer = 2, fraction = 2)
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private BigDecimal salary;

    @Size(max = 512)
    private String benefits;

    @Size(max = 512)
    private String contactInformation;

    @NotNull
    @Size(max = 512)
    private String education;

    @Size(max = 512)
    private String skills;

    @Size(max = 1024)
    private String performanceReviews;

    @NotNull
    private Long person;

    @NotNull
    private Long employeeStatus;

    @NotNull
    private Long jobTitle;

    @NotNull
    private Long department;

    @NotNull
    private Long manager;

}

package com.isiweek.employee;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.constraints.Digits;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.OffsetDateTime;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
@Setter
public class EmployeeDTO {

    private Long id;

    @NotNull
    private LocalDate dateOfHire;

    @NotNull
    @Digits(integer = 4, fraction = 2)
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private BigDecimal salary;

    @NotNull
    @DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm:ssXXX")
    private OffsetDateTime dateCreated;

    @NotNull
    @DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm:ssXXX")
    private OffsetDateTime lastUpdated;

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
    private Long employeeStatus;

    @NotNull
    private Long department;

    @NotNull
    private Long manager;

    @NotNull
    private Long person;

    @NotNull
    private Long jobTitle;

}

package com.isiweek.customer.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.constraints.Digits;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import java.math.BigDecimal;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CustomerDTO {

    private Long id;

    @NotNull
    private Long creditScore;

    @NotNull
    @Digits(integer = 2, fraction = 2)
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private BigDecimal maxLoanAmount;

    @NotNull
    @Size(max = 256)
    private String observations;

    @NotNull
    private Long person;

}

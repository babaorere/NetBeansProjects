package com.isiweek.loan_contract.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.constraints.Digits;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import java.math.BigDecimal;
import java.time.LocalDate;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class LoanContractDTO {

    private Long id;

    @NotNull
    @Digits(integer = 2, fraction = 2)
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private BigDecimal loanAmount;

    @NotNull
    @Digits(integer = 2, fraction = 2)
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private BigDecimal interestRate;

    @NotNull
    private Long loanTerm;

    @NotNull
    @Digits(integer = 2, fraction = 2)
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private BigDecimal payment;

    @NotNull
    private LocalDate date;

    @NotNull
    private LocalDate dateOfMaturity;

    @NotNull
    private Long loanPurpose;

    @Size(max = 1024)
    private String collateral;

    @Digits(integer = 2, fraction = 2)
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private BigDecimal prepaymentPenalty;

    @Digits(integer = 2, fraction = 2)
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private BigDecimal defaultInterestRate;

    @NotNull
    private Long loanType;

    @NotNull
    private Long loanStatus;

    @NotNull
    private Long customer;

}

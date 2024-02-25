package com.isiweek.loan_contract;

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
public class LoanContractDTO {

    private Long id;

    @NotNull
    private LocalDate date;

    @NotNull
    private LocalDate dateOfMaturity;

    @Digits(integer = 4, fraction = 2)
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private BigDecimal defaultInterestRate;

    @NotNull
    @Digits(integer = 4, fraction = 2)
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private BigDecimal interestRate;

    @NotNull
    @Digits(integer = 4, fraction = 2)
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private BigDecimal loanAmount;

    @NotNull
    @Digits(integer = 4, fraction = 2)
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private BigDecimal payment;

    @Digits(integer = 4, fraction = 2)
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private BigDecimal prepaymentPenalty;

    @NotNull
    @DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm:ssXXX")
    private OffsetDateTime dateCreated;

    @NotNull
    @DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm:ssXXX")
    private OffsetDateTime lastUpdated;

    @NotNull
    private Long loanPurpose;

    @NotNull
    private Long loanTerm;

    @Size(max = 1024)
    private String collateral;

    @NotNull
    private Long customer;

    @NotNull
    private Long loanType;

    @NotNull
    private Long loanStatus;

}

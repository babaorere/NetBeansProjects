package com.isiweek.payment_datails.model;

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
public class PaymentDatailsDTO {

    private Long id;

    @NotNull
    private LocalDate paymentDate;

    @NotNull
    @Digits(integer = 2, fraction = 2)
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private BigDecimal paymentAmount;

    @Size(max = 1024)
    private String notes;

    @NotNull
    private Long loanContract;

    @NotNull
    private Long paymentType;

    @NotNull
    private Long paymentStatus;

}

package com.isiweek.loan_collector.model;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class LoanCollectorDTO {

    private Long id;

    @NotNull
    private Long idPerson;

    @NotNull
    private Long person;

    @NotNull
    private Long lcStatus;

}

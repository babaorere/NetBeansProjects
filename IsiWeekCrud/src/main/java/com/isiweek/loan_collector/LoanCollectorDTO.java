package com.isiweek.loan_collector;

import jakarta.validation.constraints.NotNull;
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
public class LoanCollectorDTO {

    private Long id;

    @NotNull
    @DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm:ssXXX")
    private OffsetDateTime dateCreated;

    @NotNull
    @LoanCollectorIdPersonUnique
    private Long idPerson;

    @NotNull
    @DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm:ssXXX")
    private OffsetDateTime lastUpdated;

    @NotNull
    private Long person;

    @NotNull
    private Long lcStatus;

}

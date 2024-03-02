package com.isiweek.debtor;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class DebtorDTO {

    private Long id;

    @NotNull
    @Size(max = 255)
    @DebtorNameUnique
    private String name;

}

package com.isiweek.status;

import jakarta.validation.constraints.NotNull;
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
public class StatusDTO {

    private Long id;

    @NotNull
    @StatusNameUnique
    private StatusEnum name;

}

package com.isiweek.status_entity;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
@Setter
public class StatusEntityDTO {

    private Long id;

    @NotNull
    @StatusEntityNameUnique
    private StatusEnum name;

}

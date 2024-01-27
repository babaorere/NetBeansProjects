package com.isiweek.marital_status;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class MaritalStatusDTO {

    private Long id;

    @NotNull
    @Size(max = 128)
    private String name;

    @Size(max = 512)
    private String description;

}

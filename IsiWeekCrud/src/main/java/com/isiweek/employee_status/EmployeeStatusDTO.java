package com.isiweek.employee_status;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
public class EmployeeStatusDTO {

    private Long id;

    @NotNull
    @Size(max = 128)
    private String name;

    @NotNull
    @Size(max = 512)
    private String description;

}

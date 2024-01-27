package com.isiweek.job_title.model;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
public class JobTitleDTO {

    private Long id;

    @NotNull
    @Size(max = 128)
    private String name;

    @Size(max = 512)
    private String description;

}

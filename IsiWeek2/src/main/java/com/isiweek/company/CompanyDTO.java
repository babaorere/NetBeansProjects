package com.isiweek.company;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import java.time.OffsetDateTime;
import lombok.Getter;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;


@Getter
@Setter
public class CompanyDTO {

    private Long id;

    @NotNull
    @DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm:ssXXX")
    private OffsetDateTime dateCreated;

    @NotNull
    @DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm:ssXXX")
    private OffsetDateTime lastUpdated;

    @NotNull
    @Size(max = 32)
    private String phone1;

    @Size(max = 32)
    private String phone2;

    @NotNull
    @Size(max = 64)
    @CompanyEmailUnique
    private String email;

    @NotNull
    @Size(max = 64)
    @CompanyTaxidnumberUnique
    private String taxidnumber;

    @NotNull
    @Size(max = 128)
    @CompanyNameUnique
    private String name;

    @NotNull
    @Size(max = 256)
    private String address;

    @NotNull
    @Size(max = 256)
    private String primaryContact;

    @NotNull
    @Size(max = 512)
    private String description;

    @NotNull
    private Long status;

}

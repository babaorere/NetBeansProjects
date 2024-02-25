package com.isiweek.person;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import java.time.LocalDate;
import java.time.OffsetDateTime;
import lombok.Getter;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;


@Getter
@Setter
public class PersonDTO {

    private Long id;

    @NotNull
    private LocalDate dateOfBirth;

    @NotNull
    private Boolean gender;

    @NotNull
    @DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm:ssXXX")
    private OffsetDateTime dateCreated;

    @NotNull
    @DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm:ssXXX")
    private OffsetDateTime lastUpdated;

    @NotNull
    @Size(max = 16)
    @PersonIdDocUnique
    private String idDoc;

    @NotNull
    @Size(max = 16)
    private String phoneNumber1;

    @Size(max = 16)
    private String phoneNumber2;

    @NotNull
    @Size(max = 32)
    @PersonEmailUnique
    private String email;

    @NotNull
    @Size(max = 64)
    private String city;

    @NotNull
    @Size(max = 64)
    private String firstName;

    @NotNull
    @Size(max = 64)
    private String lastName;

    @NotNull
    @Size(max = 64)
    private String state;

    @NotNull
    @Size(max = 256)
    private String address;

    @Size(max = 256)
    private String observations;

    @Size(max = 256)
    private String occupation;

    @Size(max = 255)
    private String photo;

    @NotNull
    private Long docType;

    @NotNull
    private Long criminalRecord;

    @NotNull
    private Long country;

    @NotNull
    private Long maritalStatus;

    @NotNull
    private Long user;

}

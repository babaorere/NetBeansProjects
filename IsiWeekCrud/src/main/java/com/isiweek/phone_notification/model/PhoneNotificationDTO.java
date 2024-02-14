package com.isiweek.phone_notification.model;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import java.time.LocalDateTime;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PhoneNotificationDTO {

    private Long id;

    @NotNull
    @Size(max = 256)
    private String subject;

    @NotNull
    @Size(max = 256)
    private String sentAt;

    @NotNull
    @Size(max = 1024)
    private String body;

    @NotNull
    private LocalDateTime dateOfCall;

    @NotNull
    private Long person;

    @NotNull
    private Long loanContract;

}
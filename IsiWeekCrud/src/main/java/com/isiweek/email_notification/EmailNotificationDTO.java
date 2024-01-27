package com.isiweek.email_notification;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import java.time.LocalDateTime;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class EmailNotificationDTO {

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
    private LocalDateTime dateSent;

    @NotNull
    private Long person;

    @NotNull
    private Long loanContract;

}

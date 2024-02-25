package com.isiweek.email_notification;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import java.time.OffsetDateTime;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
@Setter
public class EmailNotificationDTO {

    private Long id;

    @NotNull
    @DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm:ssXXX")
    private OffsetDateTime dateCreated;

    @NotNull
    @DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm:ssXXX")
    private OffsetDateTime dateSent;

    @NotNull
    @DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm:ssXXX")
    private OffsetDateTime lastUpdated;

    @NotNull
    @Size(max = 256)
    private String sentAt;

    @NotNull
    @Size(max = 256)
    private String subject;

    @NotNull
    @Size(max = 1024)
    private String body;

    @NotNull
    private Long loanContract;

    @NotNull
    private Long person;

}

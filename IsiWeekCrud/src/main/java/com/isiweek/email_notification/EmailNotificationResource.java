package com.isiweek.email_notification;

import com.isiweek.email_notification.EmailNotificationDTO;
import jakarta.validation.Valid;
import java.util.List;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping(value = "/api/emailNotifications", produces = MediaType.APPLICATION_JSON_VALUE)
public class EmailNotificationResource {

    private final EmailNotificationService emailNotificationService;

    public EmailNotificationResource(final EmailNotificationService emailNotificationService) {
        this.emailNotificationService = emailNotificationService;
    }

    @GetMapping
    public ResponseEntity<List<EmailNotificationDTO>> getAllEmailNotifications() {
        return ResponseEntity.ok(emailNotificationService.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<EmailNotificationDTO> getEmailNotification(
            @PathVariable(name = "id") final Long id) {
        return ResponseEntity.ok(emailNotificationService.get(id));
    }

    @PostMapping
    public ResponseEntity<Long> createEmailNotification(
            @RequestBody @Valid final EmailNotificationDTO emailNotificationDTO) {
        final Long createdId = emailNotificationService.create(emailNotificationDTO);
        return new ResponseEntity<>(createdId, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Long> updateEmailNotification(@PathVariable(name = "id") final Long id,
            @RequestBody @Valid final EmailNotificationDTO emailNotificationDTO) {
        emailNotificationService.update(id, emailNotificationDTO);
        return ResponseEntity.ok(id);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteEmailNotification(@PathVariable(name = "id") final Long id) {
        emailNotificationService.delete(id);
        return ResponseEntity.noContent().build();
    }

}

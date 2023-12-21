package com.isiweekloan.controller;

import com.isiweekloan.entity.EmailNotificationEntity;
import com.isiweekloan.repository.EmailNotificationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.HttpStatus;

import java.util.List;
import java.util.Optional;
import org.springframework.validation.annotation.Validated;

@RestController
@RequestMapping("/api/email-notifications")
public class EmailNotificationController {

    private final EmailNotificationRepository emailNotificationRepository;

    @Autowired
    public EmailNotificationController(EmailNotificationRepository emailNotificationRepository) {
        this.emailNotificationRepository = emailNotificationRepository;
    }

    @GetMapping
    public ResponseEntity<List<EmailNotificationEntity>> getAllEmailNotifications() {
        return ResponseEntity.ok(emailNotificationRepository.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<EmailNotificationEntity> getEmailNotificationById(@PathVariable Long id) {
        Optional<EmailNotificationEntity> optionalEmailNotification = emailNotificationRepository.findById(id);
        return optionalEmailNotification.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<EmailNotificationEntity> createEmailNotification(@Validated @RequestBody EmailNotificationEntity emailNotificationEntity) {
        validateEmailNotification(emailNotificationEntity);
        EmailNotificationEntity createdEmailNotification = emailNotificationRepository.save(emailNotificationEntity);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdEmailNotification);
    }

    @PutMapping("/{id}")
    public ResponseEntity<EmailNotificationEntity> updateEmailNotification(@PathVariable Long id, @Validated @RequestBody EmailNotificationEntity emailNotificationEntity) {
        Optional<EmailNotificationEntity> optionalExistingEmailNotification = emailNotificationRepository.findById(id);

        return optionalExistingEmailNotification.map(existingEmailNotification -> {
            validateEmailNotification(emailNotificationEntity);
            // Update fields if needed
            existingEmailNotification.setSubject(emailNotificationEntity.getSubject());
            existingEmailNotification.setSentAt(emailNotificationEntity.getSentAt());
            existingEmailNotification.setBody(emailNotificationEntity.getBody());
            return ResponseEntity.ok(emailNotificationRepository.save(existingEmailNotification));
        }).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteEmailNotification(@PathVariable Long id) {
        if (emailNotificationRepository.existsById(id)) {
            emailNotificationRepository.deleteById(id);
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    private void validateEmailNotification(EmailNotificationEntity emailNotificationEntity) {
        if (emailNotificationEntity.getSubject() == null || emailNotificationEntity.getSentAt() == null || emailNotificationEntity.getBody() == null) {
            throw new IllegalArgumentException("Subject, sentAt, and body cannot be null");
        }

        // Additional validation if needed
    }

    // Exception handling
    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<String> handleIllegalArgumentException(IllegalArgumentException e) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<String> handleException(Exception e) {
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("An error occurred");
    }
}

package com.isiweekloan.service;

import com.isiweekloan.entity.EmailNotificationEntity;
import com.isiweekloan.repository.EmailNotificationRepository;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class EmailNotificationService {

    private final EmailNotificationRepository emailNotificationRepository;

    @Autowired
    public EmailNotificationService(EmailNotificationRepository emailNotificationRepository) {
        this.emailNotificationRepository = emailNotificationRepository;
    }

    /**
     * Retrieves all EmailNotification entities.
     *
     * @return List of EmailNotificationEntity
     */
    @Transactional(readOnly = true)
    public List<EmailNotificationEntity> getAllEmailNotifications() {
        return emailNotificationRepository.findAll();
    }

    /**
     * Retrieves an EmailNotification entity by its ID.
     *
     * @param id EmailNotification ID
     * @return ResponseEntity with EmailNotificationEntity if found, or 404 Not Found if not found
     */
    @Transactional(readOnly = true)
    public ResponseEntity<EmailNotificationEntity> getEmailNotificationById(Long id) {
        Optional<EmailNotificationEntity> optionalEmailNotification = emailNotificationRepository.findById(id);
        return optionalEmailNotification.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    /**
     * Creates a new EmailNotification entity.
     *
     * @param emailNotificationEntity EmailNotificationEntity to be created
     * @return ResponseEntity with created EmailNotificationEntity
     */
    @Transactional(readOnly = true)
    public ResponseEntity<EmailNotificationEntity> createEmailNotification(EmailNotificationEntity emailNotificationEntity) {
        validateEmailNotification(emailNotificationEntity);
        EmailNotificationEntity createdEmailNotification = emailNotificationRepository.save(emailNotificationEntity);
        return ResponseEntity.ok(createdEmailNotification);
    }

    /**
     * Updates an existing EmailNotification entity by its ID.
     *
     * @param id EmailNotification ID
     * @param emailNotificationEntity Updated EmailNotificationEntity
     * @return ResponseEntity with updated EmailNotificationEntity if found, or 404 Not Found if not found
     */
    @Transactional
    public ResponseEntity<EmailNotificationEntity> updateEmailNotification(Long id, EmailNotificationEntity emailNotificationEntity) {
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

    /**
     * Deletes an EmailNotification entity by its ID.
     *
     * @param id EmailNotification ID
     * @return ResponseEntity with no content if deleted successfully, or 404 Not Found if not found
     */
    @Transactional
    public ResponseEntity<Void> deleteEmailNotification(Long id) {
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
}

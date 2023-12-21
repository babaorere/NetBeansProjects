package com.isiweekloan.service;

import com.isiweekloan.entity.PhoneNotificationEntity;
import com.isiweekloan.repository.PhoneNotificationRepository;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class PhoneNotificationService {

    private final PhoneNotificationRepository phoneNotificationRepository;

    @Autowired
    public PhoneNotificationService(PhoneNotificationRepository phoneNotificationRepository) {
        this.phoneNotificationRepository = phoneNotificationRepository;
    }

    @Transactional(readOnly = true)
    public List<PhoneNotificationEntity> getAllPhoneNotifications() {
        return phoneNotificationRepository.findAll();
    }

    @Transactional(readOnly = true)
    public Optional<PhoneNotificationEntity> getPhoneNotificationById(Long id) {
        return phoneNotificationRepository.findById(id);
    }

    @Transactional
    public PhoneNotificationEntity createPhoneNotification(PhoneNotificationEntity phoneNotificationEntity) {
        validatePhoneNotification(phoneNotificationEntity);
        return phoneNotificationRepository.save(phoneNotificationEntity);
    }

    @Transactional
    public Optional<PhoneNotificationEntity> updatePhoneNotification(Long id, PhoneNotificationEntity phoneNotificationEntity) {
        Objects.requireNonNull(id, "Phone Notification ID cannot be null");
        validatePhoneNotification(phoneNotificationEntity);

        return phoneNotificationRepository.findById(id)
                .map(existingPhoneNotification -> {
                    // Update fields as needed
                    existingPhoneNotification.setSubject(phoneNotificationEntity.getSubject());
                    existingPhoneNotification.setBody(phoneNotificationEntity.getBody());
                    // Update other fields similarly

                    return phoneNotificationRepository.save(existingPhoneNotification);
                });
    }

    @Transactional
    public boolean deletePhoneNotification(Long id) {
        Objects.requireNonNull(id, "Phone Notification ID cannot be null");

        if (phoneNotificationRepository.existsById(id)) {
            phoneNotificationRepository.deleteById(id);
            return true;
        } else {
            return false;
        }
    }

    private void validatePhoneNotification(PhoneNotificationEntity phoneNotificationEntity) {
        // Implement validation logic based on your requirements
        // For example, check for null values, required attributes, etc.
    }
}

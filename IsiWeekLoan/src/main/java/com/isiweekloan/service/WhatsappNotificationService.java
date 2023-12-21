package com.isiweekloan.service;

import com.isiweekloan.entity.WhatsappNotificationEntity;
import com.isiweekloan.exception.NotFoundException;
import com.isiweekloan.repository.WhatsappNotificationRepository;
import com.isiweekloan.exception.BadRequestException;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class WhatsappNotificationService {

    private final WhatsappNotificationRepository whatsappNotificationRepository;

    @Autowired
    public WhatsappNotificationService(WhatsappNotificationRepository whatsappNotificationRepository) {
        this.whatsappNotificationRepository = whatsappNotificationRepository;
    }

    @Transactional(readOnly = true)
    public WhatsappNotificationEntity getWhatsappNotificationById(Long id) throws NotFoundException {
        return whatsappNotificationRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("WhatsappNotification not found with id: " + id));
    }

    @Transactional(readOnly = true)
    public List<WhatsappNotificationEntity> getAllWhatsappNotifications() {
        return whatsappNotificationRepository.findAll();
    }

    @Transactional
    public WhatsappNotificationEntity createWhatsappNotification(WhatsappNotificationEntity whatsappNotification) {
        // Additional validation and business logic if needed
        return whatsappNotificationRepository.save(whatsappNotification);
    }

    @Transactional
    public WhatsappNotificationEntity updateWhatsappNotification(Long id, WhatsappNotificationEntity whatsappNotification) throws NotFoundException {
        // Additional validation and business logic if needed
        WhatsappNotificationEntity existingNotification = getWhatsappNotificationById(id);
        existingNotification.setSubject(whatsappNotification.getSubject());
        existingNotification.setSentAt(whatsappNotification.getSentAt());
        existingNotification.setBody(whatsappNotification.getBody());
        return whatsappNotificationRepository.save(existingNotification);
    }

    @Transactional
    public void deleteWhatsappNotification(Long id) {
        whatsappNotificationRepository.deleteById(id);
    }
}

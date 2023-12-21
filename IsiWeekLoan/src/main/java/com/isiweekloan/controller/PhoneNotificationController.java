package com.isiweekloan.controller;

import com.isiweekloan.entity.PhoneNotificationEntity;
import com.isiweekloan.service.PhoneNotificationService;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/phone-notifications")
public class PhoneNotificationController {

    private final PhoneNotificationService phoneNotificationService;

    @Autowired
    public PhoneNotificationController(PhoneNotificationService phoneNotificationService) {
        this.phoneNotificationService = phoneNotificationService;
    }

    @GetMapping
    public List<PhoneNotificationEntity> getAllPhoneNotifications() {
        return phoneNotificationService.getAllPhoneNotifications();
    }

    @GetMapping("/{id}")
    public ResponseEntity<PhoneNotificationEntity> getPhoneNotificationById(@PathVariable Long id) {
        return phoneNotificationService.getPhoneNotificationById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<PhoneNotificationEntity> createPhoneNotification(@Validated @RequestBody PhoneNotificationEntity phoneNotificationEntity) {
        return ResponseEntity.ok(phoneNotificationService.createPhoneNotification(phoneNotificationEntity));
    }

    @PutMapping("/{id}")
    public ResponseEntity<PhoneNotificationEntity> updatePhoneNotification(@PathVariable Long id, @Validated @RequestBody PhoneNotificationEntity phoneNotificationEntity) {
        return phoneNotificationService.updatePhoneNotification(id, phoneNotificationEntity)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletePhoneNotification(@PathVariable Long id) {
        return phoneNotificationService.deletePhoneNotification(id)
                ? ResponseEntity.noContent().build()
                : ResponseEntity.notFound().build();
    }
}

package com.isiweek.phone_notification;

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
@RequestMapping(value = "/api/phoneNotifications", produces = MediaType.APPLICATION_JSON_VALUE)
public class PhoneNotificationResource {

    private final PhoneNotificationService phoneNotificationService;

    public PhoneNotificationResource(final PhoneNotificationService phoneNotificationService) {
        this.phoneNotificationService = phoneNotificationService;
    }

    @GetMapping
    public ResponseEntity<List<PhoneNotificationDTO>> getAllPhoneNotifications() {
        return ResponseEntity.ok(phoneNotificationService.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<PhoneNotificationDTO> getPhoneNotification(
            @PathVariable(name = "id") final Long id) {
        return ResponseEntity.ok(phoneNotificationService.get(id));
    }

    @PostMapping
    public ResponseEntity<Long> createPhoneNotification(
            @RequestBody @Valid final PhoneNotificationDTO phoneNotificationDTO) {
        final Long createdId = phoneNotificationService.create(phoneNotificationDTO);
        return new ResponseEntity<>(createdId, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Long> updatePhoneNotification(@PathVariable(name = "id") final Long id,
            @RequestBody @Valid final PhoneNotificationDTO phoneNotificationDTO) {
        phoneNotificationService.update(id, phoneNotificationDTO);
        return ResponseEntity.ok(id);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletePhoneNotification(@PathVariable(name = "id") final Long id) {
        phoneNotificationService.delete(id);
        return ResponseEntity.noContent().build();
    }

}

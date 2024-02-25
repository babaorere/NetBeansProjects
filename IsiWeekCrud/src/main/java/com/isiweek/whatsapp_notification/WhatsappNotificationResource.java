package com.isiweek.whatsapp_notification;

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
@RequestMapping(value = "/api/whatsappNotifications", produces = MediaType.APPLICATION_JSON_VALUE)
public class WhatsappNotificationResource {

    private final WhatsappNotificationService whatsappNotificationService;

    public WhatsappNotificationResource(
            final WhatsappNotificationService whatsappNotificationService) {
        this.whatsappNotificationService = whatsappNotificationService;
    }

    @GetMapping
    public ResponseEntity<List<WhatsappNotificationDTO>> getAllWhatsappNotifications() {
        return ResponseEntity.ok(whatsappNotificationService.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<WhatsappNotificationDTO> getWhatsappNotification(
            @PathVariable(name = "id") final Long id) {
        return ResponseEntity.ok(whatsappNotificationService.get(id));
    }

    @PostMapping
    public ResponseEntity<Long> createWhatsappNotification(
            @RequestBody @Valid final WhatsappNotificationDTO whatsappNotificationDTO) {
        final Long createdId = whatsappNotificationService.create(whatsappNotificationDTO);
        return new ResponseEntity<>(createdId, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Long> updateWhatsappNotification(@PathVariable(name = "id") final Long id,
            @RequestBody @Valid final WhatsappNotificationDTO whatsappNotificationDTO) {
        whatsappNotificationService.update(id, whatsappNotificationDTO);
        return ResponseEntity.ok(id);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteWhatsappNotification(
            @PathVariable(name = "id") final Long id) {
        whatsappNotificationService.delete(id);
        return ResponseEntity.noContent().build();
    }

}

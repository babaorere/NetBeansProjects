package com.isiweekloan.controller;

import com.isiweekloan.entity.WhatsappNotificationEntity;
import com.isiweekloan.service.WhatsappNotificationService;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/whatsapp-notifications")
public class WhatsappNotificationController {

    private final WhatsappNotificationService whatsappNotificationService;

    @Autowired
    public WhatsappNotificationController(WhatsappNotificationService whatsappNotificationService) {
        this.whatsappNotificationService = whatsappNotificationService;
    }

    @GetMapping
    public ResponseEntity<List<WhatsappNotificationEntity>> getAllWhatsappNotifications() {
        try {
            List<WhatsappNotificationEntity> notifications = whatsappNotificationService.getAllWhatsappNotifications();
            return ResponseEntity.ok(notifications);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<WhatsappNotificationEntity> getWhatsappNotificationById(@PathVariable Long id) {
        try {
            WhatsappNotificationEntity notification = whatsappNotificationService.getWhatsappNotificationById(id);
            return ResponseEntity.ok(notification);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }

    // Otros métodos para crear, actualizar y eliminar
    @ExceptionHandler(Exception.class)
    public ResponseEntity<String> handleException(Exception e) {
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
    }
}

package com.isiweekloan.controller;

import com.isiweekloan.dto.WhatsappNotificationDto;
import com.isiweekloan.entity.WhatsappNotificationEntity;
import com.isiweekloan.exception.ResourceNotFoundException;
import com.isiweekloan.mapper.WhatsappNotificationMapper;
import com.isiweekloan.service.WhatsappNotificationService;
import io.swagger.annotations.Api;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RequestMapping("/whatsapp-notification")
@RestController
@Slf4j
@Api("whatsapp-notification")
public class WhatsappNotificationController {
    private final WhatsappNotificationService whatsappNotificationService;

    public WhatsappNotificationController(WhatsappNotificationService whatsappNotificationService) {
        this.whatsappNotificationService = whatsappNotificationService;
    }

    @PostMapping
    public ResponseEntity<Void> save(@RequestBody @Validated WhatsappNotificationDto whatsappNotificationDto) {
        whatsappNotificationService.save(whatsappNotificationDto);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/{id}")
    public ResponseEntity<WhatsappNotificationDto> findById(@PathVariable("id") Long id) {
        WhatsappNotificationDto whatsappNotification = whatsappNotificationService.findById(id);
        return ResponseEntity.ok(whatsappNotification);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable("id") Long id) {
        try {
            WhatsappNotificationDto whatsappNotificationDto = Optional.ofNullable(whatsappNotificationService.findById(id))
                    .orElseThrow(() -> {
                        log.error("Unable to delete non-existent data with ID {}", id);
                        return new ResourceNotFoundException("Unable to delete non-existent data with ID " + id);
                    });

            whatsappNotificationService.deleteById(id);
            log.info("Data with ID {} deleted successfully", id);

            return ResponseEntity.ok().build();
        } catch (Exception e) {
            log.error("Error deleting data with ID {}: {}", id, e.getMessage());
            // Puedes lanzar una excepción diferente o manejarla de otra manera según tus requisitos.
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @GetMapping("/page-query")
    public ResponseEntity<Page<WhatsappNotificationDto>> pageQuery(WhatsappNotificationDto whatsappNotificationDto, @PageableDefault(sort = "createAt", direction = Sort.Direction.DESC) Pageable pageable) {
        Page<WhatsappNotificationDto> whatsappNotificationPage = whatsappNotificationService.findByCondition(whatsappNotificationDto, pageable);
        return ResponseEntity.ok(whatsappNotificationPage);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Void> update(@RequestBody @Validated WhatsappNotificationDto whatsappNotificationDto, @PathVariable("id") Long id) {
        whatsappNotificationService.update(whatsappNotificationDto, id);
        return ResponseEntity.ok().build();
    }
}

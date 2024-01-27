package com.isiweekloan.controller;

import com.isiweekloan.dto.PhoneNotificationDto;
import com.isiweekloan.entity.PhoneNotificationEntity;
import com.isiweekloan.exception.ResourceNotFoundException;
import com.isiweekloan.mapper.PhoneNotificationMapper;
import com.isiweekloan.service.PhoneNotificationService;
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

@RequestMapping("/phone-notification")
@RestController
@Slf4j
@Api("phone-notification")
public class PhoneNotificationController {
    private final PhoneNotificationService phoneNotificationService;

    public PhoneNotificationController(PhoneNotificationService phoneNotificationService) {
        this.phoneNotificationService = phoneNotificationService;
    }

    @PostMapping
    public ResponseEntity<Void> save(@RequestBody @Validated PhoneNotificationDto phoneNotificationDto) {
        phoneNotificationService.save(phoneNotificationDto);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/{id}")
    public ResponseEntity<PhoneNotificationDto> findById(@PathVariable("id") Long id) {
        PhoneNotificationDto phoneNotification = phoneNotificationService.findById(id);
        return ResponseEntity.ok(phoneNotification);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable("id") Long id) {
        try {
            PhoneNotificationDto phoneNotificationDto = Optional.ofNullable(phoneNotificationService.findById(id))
                    .orElseThrow(() -> {
                        log.error("Unable to delete non-existent data with ID {}", id);
                        return new ResourceNotFoundException("Unable to delete non-existent data with ID " + id);
                    });

            phoneNotificationService.deleteById(id);
            log.info("Data with ID {} deleted successfully", id);

            return ResponseEntity.ok().build();
        } catch (Exception e) {
            log.error("Error deleting data with ID {}: {}", id, e.getMessage());
            // Puedes lanzar una excepción diferente o manejarla de otra manera según tus requisitos.
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }


    @GetMapping("/page-query")
    public ResponseEntity<Page<PhoneNotificationDto>> pageQuery(PhoneNotificationDto phoneNotificationDto, @PageableDefault(sort = "createAt", direction = Sort.Direction.DESC) Pageable pageable) {
        Page<PhoneNotificationDto> phoneNotificationPage = phoneNotificationService.findByCondition(phoneNotificationDto, pageable);
        return ResponseEntity.ok(phoneNotificationPage);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Void> update(@RequestBody @Validated PhoneNotificationDto phoneNotificationDto, @PathVariable("id") Long id) {
        phoneNotificationService.update(phoneNotificationDto, id);
        return ResponseEntity.ok().build();
    }
}

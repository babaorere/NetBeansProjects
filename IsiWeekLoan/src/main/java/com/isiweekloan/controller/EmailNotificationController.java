package com.isiweekloan.controller;

import com.isiweekloan.dto.EmailNotificationDto;
import com.isiweekloan.entity.EmailNotificationEntity;
import com.isiweekloan.exception.ResourceNotFoundException;
import com.isiweekloan.mapper.EmailNotificationMapper;
import com.isiweekloan.service.EmailNotificationService;
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

@RequestMapping("/email-notification")
@RestController
@Slf4j
@Api("email-notification")
public class EmailNotificationController {
    private final EmailNotificationService emailNotificationService;

    public EmailNotificationController(EmailNotificationService emailNotificationService) {
        this.emailNotificationService = emailNotificationService;
    }

    @PostMapping
    public ResponseEntity<Void> save(@RequestBody @Validated EmailNotificationDto emailNotificationDto) {
        emailNotificationService.save(emailNotificationDto);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/{id}")
    public ResponseEntity<EmailNotificationDto> findById(@PathVariable("id") Long id) {
        EmailNotificationDto emailNotification = emailNotificationService.findById(id);
        return ResponseEntity.ok(emailNotification);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable("id") Long id) {
        try {
            EmailNotificationDto emailNotificationDto = emailNotificationService.findById(id);

            if (emailNotificationDto == null) {
                log.error("Unable to delete non-existent data with ID {}", id);
                throw new ResourceNotFoundException("Unable to delete non-existent data with ID " + id);
            }

            emailNotificationService.deleteById(id);
            log.info("Data with ID {} deleted successfully", id);

            return ResponseEntity.ok().build();
        } catch (Exception e) {
            log.error("Error deleting data with ID {}: {}", id, e.getMessage());
            // Puedes lanzar una excepción diferente o manejarla de otra manera según tus requisitos.
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @GetMapping("/page-query")
    public ResponseEntity<Page<EmailNotificationDto>> pageQuery(EmailNotificationDto emailNotificationDto, @PageableDefault(sort = "createAt", direction = Sort.Direction.DESC) Pageable pageable) {
        Page<EmailNotificationDto> emailNotificationPage = emailNotificationService.findByCondition(emailNotificationDto, pageable);
        return ResponseEntity.ok(emailNotificationPage);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Void> update(@RequestBody @Validated EmailNotificationDto emailNotificationDto, @PathVariable("id") Long id) {
        emailNotificationService.update(emailNotificationDto, id);
        return ResponseEntity.ok().build();
    }
}

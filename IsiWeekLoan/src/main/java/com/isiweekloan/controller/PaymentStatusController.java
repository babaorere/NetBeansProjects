package com.isiweekloan.controller;

import com.isiweekloan.dto.PaymentStatusDto;
import com.isiweekloan.entity.PaymentStatusEntity;
import com.isiweekloan.exception.ResourceNotFoundException;
import com.isiweekloan.mapper.PaymentStatusMapper;
import com.isiweekloan.service.PaymentStatusService;
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

@RequestMapping("/payment-status")
@RestController
@Slf4j
@Api("payment-status")
public class PaymentStatusController {
    private final PaymentStatusService paymentStatusService;

    public PaymentStatusController(PaymentStatusService paymentStatusService) {
        this.paymentStatusService = paymentStatusService;
    }

    @PostMapping
    public ResponseEntity<Void> save(@RequestBody @Validated PaymentStatusDto paymentStatusDto) {
        paymentStatusService.save(paymentStatusDto);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/{id}")
    public ResponseEntity<PaymentStatusDto> findById(@PathVariable("id") Long id) {
        PaymentStatusDto paymentStatus = paymentStatusService.findById(id);
        return ResponseEntity.ok(paymentStatus);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable("id") Long id) {
        try {
            PaymentStatusDto paymentStatusDto = Optional.ofNullable(paymentStatusService.findById(id))
                    .orElseThrow(() -> {
                        log.error("Unable to delete non-existent data with ID {}", id);
                        return new ResourceNotFoundException("Unable to delete non-existent data with ID " + id);
                    });

            paymentStatusService.deleteById(id);
            log.info("Data with ID {} deleted successfully", id);

            return ResponseEntity.ok().build();

        } catch (Exception e) {
            log.error("Error deleting data with ID {}: {}", id, e.getMessage());
            // Puedes lanzar una excepción diferente o manejarla de otra manera según tus requisitos.
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @GetMapping("/page-query")
    public ResponseEntity<Page<PaymentStatusDto>> pageQuery(PaymentStatusDto paymentStatusDto, @PageableDefault(sort = "createAt", direction = Sort.Direction.DESC) Pageable pageable) {
        Page<PaymentStatusDto> paymentStatusPage = paymentStatusService.findByCondition(paymentStatusDto, pageable);
        return ResponseEntity.ok(paymentStatusPage);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Void> update(@RequestBody @Validated PaymentStatusDto paymentStatusDto, @PathVariable("id") Long id) {
        paymentStatusService.update(paymentStatusDto, id);
        return ResponseEntity.ok().build();
    }
}

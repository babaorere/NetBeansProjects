package com.isiweekloan.controller;

import com.isiweekloan.dto.PaymentDetailsDto;
import com.isiweekloan.exception.ResourceNotFoundException;
import com.isiweekloan.service.PaymentDetailsService;
import io.swagger.annotations.Api;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RequestMapping("/payment-datails")
@RestController
@Slf4j
@Api("payment-datails")
public class PaymentDetailsController {
    private final PaymentDetailsService paymentDetailsService;

    public PaymentDetailsController(PaymentDetailsService paymentDatailsService) {
        this.paymentDetailsService = paymentDatailsService;
    }

    @PostMapping
    public ResponseEntity<Void> save(@RequestBody @Validated PaymentDetailsDto paymentDatailsDto) {
        paymentDetailsService.save(paymentDatailsDto);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/{id}")
    public ResponseEntity<PaymentDetailsDto> findById(@PathVariable("id") Long id) {
        PaymentDetailsDto paymentDatails = paymentDetailsService.findById(id);
        return ResponseEntity.ok(paymentDatails);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable("id") Long id) {
        try {
            PaymentDetailsDto paymentDetailsDto = Optional.ofNullable(paymentDetailsService.findById(id))
                    .orElseThrow(() -> {
                        log.error("Unable to delete non-existent data with ID {}", id);
                        return new ResourceNotFoundException("Unable to delete non-existent data with ID " + id);
                    });

            paymentDetailsService.deleteById(id);
            log.info("Data with ID {} deleted successfully", id);

            return ResponseEntity.ok().build();

        } catch (Exception e) {
            log.error("Error deleting data with ID {}: {}", id, e.getMessage());
            // Puedes lanzar una excepción diferente o manejarla de otra manera según tus requisitos.
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @GetMapping("/page-query")
    public ResponseEntity<Page<PaymentDetailsDto>> pageQuery(PaymentDetailsDto paymentDatailsDto, @PageableDefault(sort = "createAt", direction = Sort.Direction.DESC) Pageable pageable) {
        Page<PaymentDetailsDto> paymentDatailsPage = paymentDetailsService.findByCondition(paymentDatailsDto, pageable);
        return ResponseEntity.ok(paymentDatailsPage);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Void> update(@RequestBody @Validated PaymentDetailsDto paymentDatailsDto, @PathVariable("id") Long id) {
        paymentDetailsService.update(paymentDatailsDto, id);
        return ResponseEntity.ok().build();
    }
}

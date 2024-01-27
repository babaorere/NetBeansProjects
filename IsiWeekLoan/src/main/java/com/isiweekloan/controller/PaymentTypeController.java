package com.isiweekloan.controller;

import com.isiweekloan.dto.PaymentTypeDto;
import com.isiweekloan.entity.PaymentTypeEntity;
import com.isiweekloan.exception.ResourceNotFoundException;
import com.isiweekloan.mapper.PaymentTypeMapper;
import com.isiweekloan.service.PaymentTypeService;
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

@RequestMapping("/payment-type")
@RestController
@Slf4j
@Api("payment-type")
public class PaymentTypeController {
    private final PaymentTypeService paymentTypeService;

    public PaymentTypeController(PaymentTypeService paymentTypeService) {
        this.paymentTypeService = paymentTypeService;
    }

    @PostMapping
    public ResponseEntity<Void> save(@RequestBody @Validated PaymentTypeDto paymentTypeDto) {
        paymentTypeService.save(paymentTypeDto);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/{id}")
    public ResponseEntity<PaymentTypeDto> findById(@PathVariable("id") Long id) {
        PaymentTypeDto paymentType = paymentTypeService.findById(id);
        return ResponseEntity.ok(paymentType);
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable("id") Long id) {
        try {
            PaymentTypeDto paymentTypeDto = Optional.ofNullable(paymentTypeService.findById(id))
                    .orElseThrow(() -> {
                        log.error("Unable to delete non-existent data with ID {}", id);
                        return new ResourceNotFoundException("Unable to delete non-existent data with ID " + id);
                    });

            paymentTypeService.deleteById(id);
            log.info("Data with ID {} deleted successfully", id);

            return ResponseEntity.ok().build();

        } catch (Exception e) {
            log.error("Error deleting data with ID {}: {}", id, e.getMessage());
            // Puedes lanzar una excepción diferente o manejarla de otra manera según tus requisitos.
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @GetMapping("/page-query")
    public ResponseEntity<Page<PaymentTypeDto>> pageQuery(PaymentTypeDto paymentTypeDto, @PageableDefault(sort = "createAt", direction = Sort.Direction.DESC) Pageable pageable) {
        Page<PaymentTypeDto> paymentTypePage = paymentTypeService.findByCondition(paymentTypeDto, pageable);
        return ResponseEntity.ok(paymentTypePage);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Void> update(@RequestBody @Validated PaymentTypeDto paymentTypeDto, @PathVariable("id") Long id) {
        paymentTypeService.update(paymentTypeDto, id);
        return ResponseEntity.ok().build();
    }
}

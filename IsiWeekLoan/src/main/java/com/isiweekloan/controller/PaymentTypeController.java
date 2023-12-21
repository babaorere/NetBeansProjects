package com.isiweekloan.controller;

import com.isiweekloan.entity.PaymentTypeEntity;
import com.isiweekloan.service.PaymentTypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import org.springframework.validation.annotation.Validated;

@RestController
@RequestMapping("/api/payment-types")
public class PaymentTypeController {

    private final PaymentTypeService paymentTypeService;

    @Autowired
    public PaymentTypeController(PaymentTypeService paymentTypeService) {
        this.paymentTypeService = paymentTypeService;
    }

    @GetMapping
    public List<PaymentTypeEntity> getAllPaymentTypes() {
        return paymentTypeService.getAllPaymentTypes();
    }

    @GetMapping("/{id}")
    public ResponseEntity<PaymentTypeEntity> getPaymentTypeById(@PathVariable Long id) {
        return paymentTypeService.getPaymentTypeById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<PaymentTypeEntity> createPaymentType(@Validated @RequestBody PaymentTypeEntity paymentTypeEntity) {
        return ResponseEntity.ok(paymentTypeService.createPaymentType(paymentTypeEntity));
    }

    @PutMapping("/{id}")
    public ResponseEntity<PaymentTypeEntity> updatePaymentType(@PathVariable Long id, @Validated @RequestBody PaymentTypeEntity paymentTypeEntity) {
        return paymentTypeService.updatePaymentType(id, paymentTypeEntity)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletePaymentType(@PathVariable Long id) {
        return paymentTypeService.deletePaymentType(id)
                ? ResponseEntity.noContent().build()
                : ResponseEntity.notFound().build();
    }
}

package com.isiweekloan.controller;

import com.isiweekloan.entity.PaymentStatusEntity;
import com.isiweekloan.service.PaymentStatusService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import org.springframework.validation.annotation.Validated;

@RestController
@RequestMapping("/api/payment-status")
public class PaymentStatusController {

    private final PaymentStatusService paymentStatusService;

    @Autowired
    public PaymentStatusController(PaymentStatusService paymentStatusService) {
        this.paymentStatusService = paymentStatusService;
    }

    @GetMapping
    public List<PaymentStatusEntity> getAllPaymentStatus() {
        return paymentStatusService.getAllPaymentStatus();
    }

    @GetMapping("/{id}")
    public ResponseEntity<PaymentStatusEntity> getPaymentStatusById(@PathVariable Long id) {
        return paymentStatusService.getPaymentStatusById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<PaymentStatusEntity> createPaymentStatus(@Validated @RequestBody PaymentStatusEntity paymentStatusEntity) {
        return ResponseEntity.ok(paymentStatusService.createPaymentStatus(paymentStatusEntity));
    }

    @PutMapping("/{id}")
    public ResponseEntity<PaymentStatusEntity> updatePaymentStatus(@PathVariable Long id, @Validated @RequestBody PaymentStatusEntity paymentStatusEntity) {
        return paymentStatusService.updatePaymentStatus(id, paymentStatusEntity)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletePaymentStatus(@PathVariable Long id) {
        return paymentStatusService.deletePaymentStatus(id)
                ? ResponseEntity.noContent().build()
                : ResponseEntity.notFound().build();
    }
}

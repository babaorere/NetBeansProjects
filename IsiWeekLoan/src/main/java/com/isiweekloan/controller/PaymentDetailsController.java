package com.isiweekloan.controller;

import com.isiweekloan.entity.PaymentDatailsEntity;
import com.isiweekloan.service.PaymentDetailsService;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/payment-details")
public class PaymentDetailsController {

    private final PaymentDetailsService paymentDetailsService;

    @Autowired
    public PaymentDetailsController(PaymentDetailsService paymentDetailsService) {
        this.paymentDetailsService = paymentDetailsService;
    }

    @GetMapping
    public List<PaymentDatailsEntity> getAllPaymentDetails() {
        return paymentDetailsService.getAllPaymentDetails();
    }

    @GetMapping("/{id}")
    public ResponseEntity<PaymentDatailsEntity> getPaymentDetailsById(@PathVariable Long id) {
        return paymentDetailsService.getPaymentDetailsById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<PaymentDatailsEntity> createPaymentDetails(@Validated @RequestBody PaymentDatailsEntity paymentDatailsEntity) {
        return ResponseEntity.ok(paymentDetailsService.createPaymentDetails(paymentDatailsEntity));
    }

    @PutMapping("/{id}")
    public ResponseEntity<PaymentDatailsEntity> updatePaymentDetails(@PathVariable Long id, @Validated @RequestBody PaymentDatailsEntity paymentDatailsEntity) {
        return paymentDetailsService.updatePaymentDetails(id, paymentDatailsEntity)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletePaymentDetails(@PathVariable Long id) {
        return paymentDetailsService.deletePaymentDetails(id)
                ? ResponseEntity.noContent().build()
                : ResponseEntity.notFound().build();
    }
}

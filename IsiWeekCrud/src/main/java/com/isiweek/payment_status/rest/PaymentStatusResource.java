package com.isiweek.payment_status.rest;

import com.isiweek.payment_status.model.PaymentStatusDTO;
import com.isiweek.payment_status.service.PaymentStatusService;
import jakarta.validation.Valid;
import java.util.List;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping(value = "/api/paymentStatuses", produces = MediaType.APPLICATION_JSON_VALUE)
public class PaymentStatusResource {

    private final PaymentStatusService paymentStatusService;

    public PaymentStatusResource(final PaymentStatusService paymentStatusService) {
        this.paymentStatusService = paymentStatusService;
    }

    @GetMapping
    public ResponseEntity<List<PaymentStatusDTO>> getAllPaymentStatuses() {
        return ResponseEntity.ok(paymentStatusService.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<PaymentStatusDTO> getPaymentStatus(
            @PathVariable(name = "id") final Long id) {
        return ResponseEntity.ok(paymentStatusService.get(id));
    }

    @PostMapping
    public ResponseEntity<Long> createPaymentStatus(
            @RequestBody @Valid final PaymentStatusDTO paymentStatusDTO) {
        final Long createdId = paymentStatusService.create(paymentStatusDTO);
        return new ResponseEntity<>(createdId, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Long> updatePaymentStatus(@PathVariable(name = "id") final Long id,
            @RequestBody @Valid final PaymentStatusDTO paymentStatusDTO) {
        paymentStatusService.update(id, paymentStatusDTO);
        return ResponseEntity.ok(id);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletePaymentStatus(@PathVariable(name = "id") final Long id) {
        paymentStatusService.delete(id);
        return ResponseEntity.noContent().build();
    }

}

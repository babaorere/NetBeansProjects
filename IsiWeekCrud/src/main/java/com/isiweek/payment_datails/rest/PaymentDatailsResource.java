package com.isiweek.payment_datails.rest;

import com.isiweek.payment_datails.model.PaymentDatailsDTO;
import com.isiweek.payment_datails.service.PaymentDatailsService;
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
@RequestMapping(value = "/api/paymentDatailss", produces = MediaType.APPLICATION_JSON_VALUE)
public class PaymentDatailsResource {

    private final PaymentDatailsService paymentDatailsService;

    public PaymentDatailsResource(final PaymentDatailsService paymentDatailsService) {
        this.paymentDatailsService = paymentDatailsService;
    }

    @GetMapping
    public ResponseEntity<List<PaymentDatailsDTO>> getAllPaymentDatailss() {
        return ResponseEntity.ok(paymentDatailsService.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<PaymentDatailsDTO> getPaymentDatails(
            @PathVariable(name = "id") final Long id) {
        return ResponseEntity.ok(paymentDatailsService.get(id));
    }

    @PostMapping
    public ResponseEntity<Long> createPaymentDatails(
            @RequestBody @Valid final PaymentDatailsDTO paymentDatailsDTO) {
        final Long createdId = paymentDatailsService.create(paymentDatailsDTO);
        return new ResponseEntity<>(createdId, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Long> updatePaymentDatails(@PathVariable(name = "id") final Long id,
            @RequestBody @Valid final PaymentDatailsDTO paymentDatailsDTO) {
        paymentDatailsService.update(id, paymentDatailsDTO);
        return ResponseEntity.ok(id);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletePaymentDatails(@PathVariable(name = "id") final Long id) {
        paymentDatailsService.delete(id);
        return ResponseEntity.noContent().build();
    }

}

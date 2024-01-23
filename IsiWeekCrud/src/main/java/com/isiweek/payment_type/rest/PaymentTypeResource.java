package com.isiweek.payment_type.rest;

import com.isiweek.payment_type.model.PaymentTypeDTO;
import com.isiweek.payment_type.service.PaymentTypeService;
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
@RequestMapping(value = "/api/paymentTypes", produces = MediaType.APPLICATION_JSON_VALUE)
public class PaymentTypeResource {

    private final PaymentTypeService paymentTypeService;

    public PaymentTypeResource(final PaymentTypeService paymentTypeService) {
        this.paymentTypeService = paymentTypeService;
    }

    @GetMapping
    public ResponseEntity<List<PaymentTypeDTO>> getAllPaymentTypes() {
        return ResponseEntity.ok(paymentTypeService.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<PaymentTypeDTO> getPaymentType(@PathVariable(name = "id") final Long id) {
        return ResponseEntity.ok(paymentTypeService.get(id));
    }

    @PostMapping
    public ResponseEntity<Long> createPaymentType(
            @RequestBody @Valid final PaymentTypeDTO paymentTypeDTO) {
        final Long createdId = paymentTypeService.create(paymentTypeDTO);
        return new ResponseEntity<>(createdId, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Long> updatePaymentType(@PathVariable(name = "id") final Long id,
            @RequestBody @Valid final PaymentTypeDTO paymentTypeDTO) {
        paymentTypeService.update(id, paymentTypeDTO);
        return ResponseEntity.ok(id);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletePaymentType(@PathVariable(name = "id") final Long id) {
        paymentTypeService.delete(id);
        return ResponseEntity.noContent().build();
    }

}

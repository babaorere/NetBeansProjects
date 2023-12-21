package com.isiweekloan.controller;

import com.isiweekloan.entity.CustomerEntity;
import com.isiweekloan.exception.ResourceNotFoundException;
import com.isiweekloan.service.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import com.isiweekloan.exception.BadRequestException;

import java.util.List;

@RestController
@RequestMapping("/api/customers")
@Validated
public class CustomerController {

    @Autowired
    private CustomerService customerService;

    @GetMapping
    public ResponseEntity<List<CustomerEntity>> getAllCustomers() {
        List<CustomerEntity> customers = customerService.findAllCustomers();
        if (customers.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(customers);
    }

    @GetMapping("/{id}")
    public ResponseEntity<CustomerEntity> getCustomerById(@PathVariable Long id) throws ResourceNotFoundException {
        CustomerEntity customer = customerService.findCustomerById(id);
        if (customer == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(customer);
    }

    @PostMapping
    public ResponseEntity<CustomerEntity> createCustomer(@Validated @RequestBody CustomerEntity customer) throws com.isiweekloan.exception.BadRequestException {
        validateRequiredFields(customer);
        CustomerEntity createdCustomer = customerService.createCustomer(customer);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdCustomer);
    }

    @PutMapping("/{id}")
    public ResponseEntity<CustomerEntity> updateCustomer(@PathVariable Long id, @Validated @RequestBody CustomerEntity customer) throws com.isiweekloan.exception.BadRequestException, ResourceNotFoundException {
        if (!customer.getId().equals(id)) {
            throw new BadRequestException("ID in request body does not match ID in path variable.");
        }
        validateRequiredFields(customer);
        CustomerEntity updatedCustomer = customerService.updateCustomer(id, customer);
        if (updatedCustomer == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(updatedCustomer);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCustomer(@PathVariable Long id) throws ResourceNotFoundException {
        customerService.deleteCustomer(id);
        return ResponseEntity.noContent().build();
    }

    @ExceptionHandler(BadRequestException.class)
    public ResponseEntity<Void> handleBadRequestException(BadRequestException e) {
        return ResponseEntity.badRequest().build();
    }

    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<Void> handleResourceNotFoundException(ResourceNotFoundException e) {
        return ResponseEntity.notFound().build();
    }

    private void validateRequiredFields(CustomerEntity customer) throws BadRequestException {

        if (customer.getCreditScore() == null) {
            throw new BadRequestException("The credit score field is required.");
        }

        if (customer.getIdPerson() == null) {
            throw new BadRequestException("The id person field is required.");
        }

        if (customer.getMaxLoanAmount() == null) {
            throw new BadRequestException("The max loan amount field is required.");
        }

        if (customer.getObservations() == null) {
            throw new BadRequestException("The observations field is required.");
        }
    }
}

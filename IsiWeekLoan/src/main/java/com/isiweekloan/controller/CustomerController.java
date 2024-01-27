package com.isiweekloan.controller;

import com.isiweekloan.dto.CustomerDto;
import com.isiweekloan.entity.CustomerEntity;
import com.isiweekloan.exception.ResourceNotFoundException;
import com.isiweekloan.mapper.CustomerMapper;
import com.isiweekloan.service.CustomerService;
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

@RequestMapping("/customer")
@RestController
@Slf4j
@Api("customer")
public class CustomerController {
    private final CustomerService customerService;

    public CustomerController(CustomerService customerService) {
        this.customerService = customerService;
    }

    @PostMapping
    public ResponseEntity<Void> save(@RequestBody @Validated CustomerDto customerDto) {
        customerService.save(customerDto);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/{id}")
    public ResponseEntity<CustomerDto> findById(@PathVariable("id") Long id) {
        CustomerDto customer = customerService.findById(id);
        return ResponseEntity.ok(customer);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable("id") Long id) {
        try {
            CustomerDto customerDto = Optional.ofNullable(customerService.findById(id))
                    .orElseThrow(() -> {
                        log.error("Unable to delete non-existent data with ID {}", id);
                        return new ResourceNotFoundException("Unable to delete non-existent data with ID " + id);
                    });

            customerService.deleteById(id);
            log.info("Data with ID {} deleted successfully", id);

            return ResponseEntity.ok().build();

        } catch (Exception e) {
            log.error("Error deleting data with ID {}: {}", id, e.getMessage());
            // Puedes lanzar una excepción diferente o manejarla de otra manera según tus requisitos.
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @GetMapping("/page-query")
    public ResponseEntity<Page<CustomerDto>> pageQuery(CustomerDto customerDto, @PageableDefault(sort = "createAt", direction = Sort.Direction.DESC) Pageable pageable) {
        Page<CustomerDto> customerPage = customerService.findByCondition(customerDto, pageable);
        return ResponseEntity.ok(customerPage);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Void> update(@RequestBody @Validated CustomerDto customerDto, @PathVariable("id") Long id) {
        customerService.update(customerDto, id);
        return ResponseEntity.ok().build();
    }
}

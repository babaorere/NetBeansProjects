package com.isiweekloan.controller;

import com.isiweekloan.entity.LoanCollectorEntity;
import com.isiweekloan.service.LoanCollectorService;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/loan-collectors")
public class LoanCollectorController {

    private final LoanCollectorService loanCollectorService;

    @Autowired
    public LoanCollectorController(LoanCollectorService loanCollectorService) {
        this.loanCollectorService = loanCollectorService;
    }

    @GetMapping
    public List<LoanCollectorEntity> getAllLoanCollectors() {
        return loanCollectorService.getAllLoanCollectors();
    }

    @GetMapping("/{id}")
    public ResponseEntity<LoanCollectorEntity> getLoanCollectorById(@PathVariable Long id) {
        Optional<LoanCollectorEntity> loanCollectorEntity = loanCollectorService.getLoanCollectorById(id);
        return loanCollectorEntity.map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<LoanCollectorEntity> createLoanCollector(@RequestBody LoanCollectorEntity loanCollectorEntity) {
        LoanCollectorEntity createdLoanCollector = loanCollectorService.createLoanCollector(loanCollectorEntity);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdLoanCollector);
    }

    @PutMapping("/{id}")
    public ResponseEntity<LoanCollectorEntity> updateLoanCollector(
            @PathVariable Long id,
            @RequestBody LoanCollectorEntity loanCollectorEntity) {
        Optional<LoanCollectorEntity> updatedLoanCollector = loanCollectorService.updateLoanCollector(id, loanCollectorEntity);
        return updatedLoanCollector.map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteLoanCollector(@PathVariable Long id) {
        boolean isDeleted = loanCollectorService.deleteLoanCollector(id);
        return isDeleted ? ResponseEntity.noContent().build() : ResponseEntity.notFound().build();
    }

    private void validateLoanCollector(LoanCollectorEntity loanCollectorEntity) {
        Objects.requireNonNull(loanCollectorEntity.getIdPerson(), "Person ID cannot be null");
        Objects.requireNonNull(loanCollectorEntity.getIdLcStatus(), "Loan Collector Status ID cannot be null");

        // Additional validation if needed
    }
}

package com.isiweekloan.controller;

import com.isiweekloan.entity.LoanStatusEntity;
import com.isiweekloan.service.LoanStatusService;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/loan-status")
public class LoanStatusController {

    private final LoanStatusService loanStatusService;

    @Autowired
    public LoanStatusController(LoanStatusService loanStatusService) {
        this.loanStatusService = loanStatusService;
    }

    @GetMapping
    public List<LoanStatusEntity> getAllLoanStatuses() {
        return loanStatusService.getAllLoanStatuses();
    }

    @GetMapping("/{id}")
    public ResponseEntity<LoanStatusEntity> getLoanStatusById(@PathVariable Long id) {
        return loanStatusService.getLoanStatusById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<LoanStatusEntity> createLoanStatus(@Validated @RequestBody LoanStatusEntity loanStatusEntity) {
        return ResponseEntity.ok(loanStatusService.createLoanStatus(loanStatusEntity));
    }

    @PutMapping("/{id}")
    public ResponseEntity<LoanStatusEntity> updateLoanStatus(@PathVariable Long id, @Validated @RequestBody LoanStatusEntity loanStatusEntity) {
        return loanStatusService.updateLoanStatus(id, loanStatusEntity)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteLoanStatus(@PathVariable Long id) {
        return loanStatusService.deleteLoanStatus(id)
                ? ResponseEntity.noContent().build()
                : ResponseEntity.notFound().build();
    }
}

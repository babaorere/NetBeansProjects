package com.isiweekloan.controller;

import com.isiweekloan.entity.LoanCollectorStatusEntity;
import com.isiweekloan.service.LoanCollectorStatusService;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/loan-collector-status")
public class LoanCollectorStatusController {

    private final LoanCollectorStatusService loanCollectorStatusService;

    @Autowired
    public LoanCollectorStatusController(LoanCollectorStatusService loanCollectorStatusService) {
        this.loanCollectorStatusService = loanCollectorStatusService;
    }

    @GetMapping
    public List<LoanCollectorStatusEntity> getAllLoanCollectorStatus() {
        return loanCollectorStatusService.getAllLoanCollectorStatus();
    }

    @GetMapping("/{id}")
    public ResponseEntity<LoanCollectorStatusEntity> getLoanCollectorStatusById(@PathVariable Long id) {
        return loanCollectorStatusService.getLoanCollectorStatusById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<LoanCollectorStatusEntity> createLoanCollectorStatus(@Validated @RequestBody LoanCollectorStatusEntity loanCollectorStatusEntity) {
        return ResponseEntity.ok(loanCollectorStatusService.createLoanCollectorStatus(loanCollectorStatusEntity));
    }

    @PutMapping("/{id}")
    public ResponseEntity<LoanCollectorStatusEntity> updateLoanCollectorStatus(@PathVariable Long id, @Validated @RequestBody LoanCollectorStatusEntity loanCollectorStatusEntity) {
        return loanCollectorStatusService.updateLoanCollectorStatus(id, loanCollectorStatusEntity)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteLoanCollectorStatus(@PathVariable Long id) {
        return loanCollectorStatusService.deleteLoanCollectorStatus(id)
                ? ResponseEntity.noContent().build()
                : ResponseEntity.notFound().build();
    }
}

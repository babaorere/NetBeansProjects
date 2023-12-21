package com.isiweekloan.controller;

import com.isiweekloan.entity.LoanContractEntity;
import com.isiweekloan.service.LoanContractService;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/loan-contracts")
public class LoanContractController {

    private final LoanContractService loanContractService;

    @Autowired
    public LoanContractController(LoanContractService loanContractService) {
        this.loanContractService = loanContractService;
    }

    @GetMapping
    public List<LoanContractEntity> getAllLoanContracts() {
        return loanContractService.getAllLoanContracts();
    }

    @GetMapping("/{id}")
    public ResponseEntity<LoanContractEntity> getLoanContractById(@PathVariable Long id) {
        return loanContractService.getLoanContractById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<LoanContractEntity> createLoanContract(@Validated @RequestBody LoanContractEntity loanContractEntity) {
        return ResponseEntity.ok(loanContractService.createLoanContract(loanContractEntity));
    }

    @PutMapping("/{id}")
    public ResponseEntity<LoanContractEntity> updateLoanContract(@PathVariable Long id, @Validated @RequestBody LoanContractEntity loanContractEntity) {
        return loanContractService.updateLoanContract(id, loanContractEntity)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteLoanContract(@PathVariable Long id) {
        return loanContractService.deleteLoanContract(id)
                ? ResponseEntity.noContent().build()
                : ResponseEntity.notFound().build();
    }
}

package com.isiweekloan.controller;

import com.isiweekloan.entity.LoanTypeEntity;
import com.isiweekloan.service.LoanTypeService;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/loan-types")
public class LoanTypeController {

    private final LoanTypeService loanTypeService;

    @Autowired
    public LoanTypeController(LoanTypeService loanTypeService) {
        this.loanTypeService = loanTypeService;
    }

    @GetMapping
    public List<LoanTypeEntity> getAllLoanTypes() {
        return loanTypeService.getAllLoanTypes();
    }

    @GetMapping("/{id}")
    public ResponseEntity<LoanTypeEntity> getLoanTypeById(@PathVariable Long id) {
        return loanTypeService.getLoanTypeById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<LoanTypeEntity> createLoanType(@Validated @RequestBody LoanTypeEntity loanTypeEntity) {
        return ResponseEntity.ok(loanTypeService.createLoanType(loanTypeEntity));
    }

    @PutMapping("/{id}")
    public ResponseEntity<LoanTypeEntity> updateLoanType(@PathVariable Long id, @Validated @RequestBody LoanTypeEntity loanTypeEntity) {
        return loanTypeService.updateLoanType(id, loanTypeEntity)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteLoanType(@PathVariable Long id) {
        return loanTypeService.deleteLoanType(id)
                ? ResponseEntity.noContent().build()
                : ResponseEntity.notFound().build();
    }
}

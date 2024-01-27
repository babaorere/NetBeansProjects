package com.isiweek.loan_contract.rest;

import com.isiweek.loan_contract.model.LoanContractDTO;
import com.isiweek.loan_contract.service.LoanContractService;
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
@RequestMapping(value = "/api/loanContracts", produces = MediaType.APPLICATION_JSON_VALUE)
public class LoanContractResource {

    private final LoanContractService loanContractService;

    public LoanContractResource(final LoanContractService loanContractService) {
        this.loanContractService = loanContractService;
    }

    @GetMapping
    public ResponseEntity<List<LoanContractDTO>> getAllLoanContracts() {
        return ResponseEntity.ok(loanContractService.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<LoanContractDTO> getLoanContract(
            @PathVariable(name = "id") final Long id) {
        return ResponseEntity.ok(loanContractService.get(id));
    }

    @PostMapping
    public ResponseEntity<Long> createLoanContract(
            @RequestBody @Valid final LoanContractDTO loanContractDTO) {
        final Long createdId = loanContractService.create(loanContractDTO);
        return new ResponseEntity<>(createdId, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Long> updateLoanContract(@PathVariable(name = "id") final Long id,
            @RequestBody @Valid final LoanContractDTO loanContractDTO) {
        loanContractService.update(id, loanContractDTO);
        return ResponseEntity.ok(id);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteLoanContract(@PathVariable(name = "id") final Long id) {
        loanContractService.delete(id);
        return ResponseEntity.noContent().build();
    }

}

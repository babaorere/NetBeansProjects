package com.isiweek.loan_collector.rest;

import com.isiweek.loan_collector.model.LoanCollectorDTO;
import com.isiweek.loan_collector.service.LoanCollectorService;
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
@RequestMapping(value = "/api/loanCollectors", produces = MediaType.APPLICATION_JSON_VALUE)
public class LoanCollectorResource {

    private final LoanCollectorService loanCollectorService;

    public LoanCollectorResource(final LoanCollectorService loanCollectorService) {
        this.loanCollectorService = loanCollectorService;
    }

    @GetMapping
    public ResponseEntity<List<LoanCollectorDTO>> getAllLoanCollectors() {
        return ResponseEntity.ok(loanCollectorService.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<LoanCollectorDTO> getLoanCollector(
            @PathVariable(name = "id") final Long id) {
        return ResponseEntity.ok(loanCollectorService.get(id));
    }

    @PostMapping
    public ResponseEntity<Long> createLoanCollector(
            @RequestBody @Valid final LoanCollectorDTO loanCollectorDTO) {
        final Long createdId = loanCollectorService.create(loanCollectorDTO);
        return new ResponseEntity<>(createdId, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Long> updateLoanCollector(@PathVariable(name = "id") final Long id,
            @RequestBody @Valid final LoanCollectorDTO loanCollectorDTO) {
        loanCollectorService.update(id, loanCollectorDTO);
        return ResponseEntity.ok(id);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteLoanCollector(@PathVariable(name = "id") final Long id) {
        loanCollectorService.delete(id);
        return ResponseEntity.noContent().build();
    }

}

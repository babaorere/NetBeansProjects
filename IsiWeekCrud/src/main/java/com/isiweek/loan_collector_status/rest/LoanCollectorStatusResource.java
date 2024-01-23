package com.isiweek.loan_collector_status.rest;

import com.isiweek.loan_collector_status.model.LoanCollectorStatusDTO;
import com.isiweek.loan_collector_status.service.LoanCollectorStatusService;
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
@RequestMapping(value = "/api/loanCollectorStatuses", produces = MediaType.APPLICATION_JSON_VALUE)
public class LoanCollectorStatusResource {

    private final LoanCollectorStatusService loanCollectorStatusService;

    public LoanCollectorStatusResource(
            final LoanCollectorStatusService loanCollectorStatusService) {
        this.loanCollectorStatusService = loanCollectorStatusService;
    }

    @GetMapping
    public ResponseEntity<List<LoanCollectorStatusDTO>> getAllLoanCollectorStatuses() {
        return ResponseEntity.ok(loanCollectorStatusService.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<LoanCollectorStatusDTO> getLoanCollectorStatus(
            @PathVariable(name = "id") final Long id) {
        return ResponseEntity.ok(loanCollectorStatusService.get(id));
    }

    @PostMapping
    public ResponseEntity<Long> createLoanCollectorStatus(
            @RequestBody @Valid final LoanCollectorStatusDTO loanCollectorStatusDTO) {
        final Long createdId = loanCollectorStatusService.create(loanCollectorStatusDTO);
        return new ResponseEntity<>(createdId, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Long> updateLoanCollectorStatus(@PathVariable(name = "id") final Long id,
            @RequestBody @Valid final LoanCollectorStatusDTO loanCollectorStatusDTO) {
        loanCollectorStatusService.update(id, loanCollectorStatusDTO);
        return ResponseEntity.ok(id);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteLoanCollectorStatus(
            @PathVariable(name = "id") final Long id) {
        loanCollectorStatusService.delete(id);
        return ResponseEntity.noContent().build();
    }

}

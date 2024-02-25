package com.isiweek.loan_status;

import com.isiweek.util.ReferencedException;
import com.isiweek.util.ReferencedWarning;
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
@RequestMapping(value = "/api/loanStatuses", produces = MediaType.APPLICATION_JSON_VALUE)
public class LoanStatusResource {

    private final LoanStatusService loanStatusService;

    public LoanStatusResource(final LoanStatusService loanStatusService) {
        this.loanStatusService = loanStatusService;
    }

    @GetMapping
    public ResponseEntity<List<LoanStatusDTO>> getAllLoanStatuses() {
        return ResponseEntity.ok(loanStatusService.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<LoanStatusDTO> getLoanStatus(@PathVariable(name = "id") final Long id) {
        return ResponseEntity.ok(loanStatusService.get(id));
    }

    @PostMapping
    public ResponseEntity<Long> createLoanStatus(
            @RequestBody @Valid final LoanStatusDTO loanStatusDTO) {
        final Long createdId = loanStatusService.create(loanStatusDTO);
        return new ResponseEntity<>(createdId, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Long> updateLoanStatus(@PathVariable(name = "id") final Long id,
            @RequestBody @Valid final LoanStatusDTO loanStatusDTO) {
        loanStatusService.update(id, loanStatusDTO);
        return ResponseEntity.ok(id);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteLoanStatus(@PathVariable(name = "id") final Long id) {
        final ReferencedWarning referencedWarning = loanStatusService.getReferencedWarning(id);
        if (referencedWarning != null) {
            throw new ReferencedException(referencedWarning);
        }
        loanStatusService.delete(id);
        return ResponseEntity.noContent().build();
    }

}

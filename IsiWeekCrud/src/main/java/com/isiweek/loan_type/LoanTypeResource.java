package com.isiweek.loan_type;

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
@RequestMapping(value = "/api/loanTypes", produces = MediaType.APPLICATION_JSON_VALUE)
public class LoanTypeResource {

    private final LoanTypeService loanTypeService;

    public LoanTypeResource(final LoanTypeService loanTypeService) {
        this.loanTypeService = loanTypeService;
    }

    @GetMapping
    public ResponseEntity<List<LoanTypeDTO>> getAllLoanTypes() {
        return ResponseEntity.ok(loanTypeService.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<LoanTypeDTO> getLoanType(@PathVariable(name = "id") final Long id) {
        return ResponseEntity.ok(loanTypeService.get(id));
    }

    @PostMapping
    public ResponseEntity<Long> createLoanType(@RequestBody @Valid final LoanTypeDTO loanTypeDTO) {
        final Long createdId = loanTypeService.create(loanTypeDTO);
        return new ResponseEntity<>(createdId, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Long> updateLoanType(@PathVariable(name = "id") final Long id,
            @RequestBody @Valid final LoanTypeDTO loanTypeDTO) {
        loanTypeService.update(id, loanTypeDTO);
        return ResponseEntity.ok(id);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteLoanType(@PathVariable(name = "id") final Long id) {
        final ReferencedWarning referencedWarning = loanTypeService.getReferencedWarning(id);
        if (referencedWarning != null) {
            throw new ReferencedException(referencedWarning);
        }
        loanTypeService.delete(id);
        return ResponseEntity.noContent().build();
    }

}

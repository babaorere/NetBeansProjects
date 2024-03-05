package com.isiweek.debtor;

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
@RequestMapping(value = "/api/debtors", produces = MediaType.APPLICATION_JSON_VALUE)
public class DebtorResource {

    private final DebtorService debtorService;

    public DebtorResource(final DebtorService debtorService) {
        this.debtorService = debtorService;
    }

    @GetMapping
    public ResponseEntity<List<DebtorDTO>> getAllDebtors() {
        return ResponseEntity.ok(debtorService.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<DebtorDTO> getDebtor(@PathVariable(name = "id") final Long id) {
        return ResponseEntity.ok(debtorService.get(id));
    }

    @PostMapping
    public ResponseEntity<Long> createDebtor(@RequestBody @Valid final DebtorDTO debtorDTO) {
        final Long createdId = debtorService.create(debtorDTO);
        return new ResponseEntity<>(createdId, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Long> updateDebtor(@PathVariable(name = "id") final Long id,
            @RequestBody @Valid final DebtorDTO debtorDTO) {
        debtorService.update(id, debtorDTO);
        return ResponseEntity.ok(id);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteDebtor(@PathVariable(name = "id") final Long id) {
        final ReferencedWarning referencedWarning = debtorService.getReferencedWarning(id);
        if (referencedWarning != null) {
            throw new ReferencedException(referencedWarning);
        }
        debtorService.delete(id);
        return ResponseEntity.noContent().build();
    }

}

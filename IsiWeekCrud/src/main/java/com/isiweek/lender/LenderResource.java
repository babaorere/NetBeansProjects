package com.isiweek.lender;

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
@RequestMapping(value = "/api/lenders", produces = MediaType.APPLICATION_JSON_VALUE)
public class LenderResource {

    private final LenderService lenderService;

    public LenderResource(final LenderService lenderService) {
        this.lenderService = lenderService;
    }

    @GetMapping
    public ResponseEntity<List<LenderDTO>> getAllLenders() {
        return ResponseEntity.ok(lenderService.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<LenderDTO> getLender(@PathVariable(name = "id") final Long id) {
        return ResponseEntity.ok(lenderService.get(id));
    }

    @PostMapping
    public ResponseEntity<Long> createLender(@RequestBody @Valid final LenderDTO lenderDTO) {
        final Long createdId = lenderService.create(lenderDTO);
        return new ResponseEntity<>(createdId, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Long> updateLender(@PathVariable(name = "id") final Long id,
            @RequestBody @Valid final LenderDTO lenderDTO) {
        lenderService.update(id, lenderDTO);
        return ResponseEntity.ok(id);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteLender(@PathVariable(name = "id") final Long id) {
        final ReferencedWarning referencedWarning = lenderService.getReferencedWarning(id);
        if (referencedWarning != null) {
            throw new ReferencedException(referencedWarning);
        }
        lenderService.delete(id);
        return ResponseEntity.noContent().build();
    }

}

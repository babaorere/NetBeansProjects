package com.isiweek.status_entity;

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
@RequestMapping(value = "/api/statusEntities", produces = MediaType.APPLICATION_JSON_VALUE)
public class StatusEntityResource {

    private final StatusEntityService statusEntityService;

    public StatusEntityResource(final StatusEntityService statusEntityService) {
        this.statusEntityService = statusEntityService;
    }

    @GetMapping
    public ResponseEntity<List<StatusEntityDTO>> getAllStatusEntities() {
        return ResponseEntity.ok(statusEntityService.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<StatusEntityDTO> getStatusEntity(
            @PathVariable(name = "id") final Long id) {
        return ResponseEntity.ok(statusEntityService.get(id));
    }

    @PostMapping
    public ResponseEntity<Long> createStatusEntity(
            @RequestBody @Valid final StatusEntityDTO statusEntityDTO) {
        final Long createdId = statusEntityService.create(statusEntityDTO);
        return new ResponseEntity<>(createdId, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Long> updateStatusEntity(@PathVariable(name = "id") final Long id,
            @RequestBody @Valid final StatusEntityDTO statusEntityDTO) {
        statusEntityService.update(id, statusEntityDTO);
        return ResponseEntity.ok(id);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteStatusEntity(@PathVariable(name = "id") final Long id) {
        final ReferencedWarning referencedWarning = statusEntityService.getReferencedWarning(id);
        if (referencedWarning != null) {
            throw new ReferencedException(referencedWarning);
        }
        statusEntityService.delete(id);
        return ResponseEntity.noContent().build();
    }

}

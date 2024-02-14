package com.isiweek.status;

import com.isiweek.util.ReferencedException;
import com.isiweek.util.ReferencedWarning;
import jakarta.validation.Valid;
import java.util.List;
import java.util.Objects;
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
@RequestMapping(value = "/api/statuses", produces = MediaType.APPLICATION_JSON_VALUE)
public class StatusResource {

    private final StatusService statusService;

    public StatusResource(final StatusService statusService) {
        this.statusService = Objects.requireNonNull(statusService, "statusService must not be null");
    }

    @GetMapping
    public ResponseEntity<List<Status>> getAllStatuses() {
        return ResponseEntity.ok(statusService.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Status> getStatus(@PathVariable(name = "id") final Long id) {
        return statusService.get(id)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<Long> createStatus(@RequestBody @Valid final Status status) {
        try {
            final Long createdId = statusService.create(status).getId();
            return new ResponseEntity<>(createdId, HttpStatus.CREATED);
        } catch (Exception e) {
            // Handle unexpected errors during creation
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<Long> updateStatus(@PathVariable(name = "id") final Long id,
            @RequestBody @Valid final Status status) {
        try {
            statusService.update(id, status);
            return ResponseEntity.ok(id);
        } catch (Exception e) {
            // Handle unexpected errors during update
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteStatus(@PathVariable(name = "id") final Long id) {
        try {
            final ReferencedWarning referencedWarning = statusService.getReferencedWarning(id);
            if (referencedWarning != null) {
                throw new ReferencedException(referencedWarning);
            }
            statusService.delete(id);
            return ResponseEntity.noContent().build();
        } catch (ReferencedException e) {
            // Handle referenced warning exception
            return ResponseEntity.status(HttpStatus.CONFLICT).body(e.getMessage());
        } catch (Exception e) {
            // Handle other unexpected errors during deletion
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

}

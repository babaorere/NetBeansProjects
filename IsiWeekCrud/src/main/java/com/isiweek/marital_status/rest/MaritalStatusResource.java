package com.isiweek.marital_status.rest;

import com.isiweek.marital_status.model.MaritalStatusDTO;
import com.isiweek.marital_status.service.MaritalStatusService;
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
@RequestMapping(value = "/api/maritalStatuses", produces = MediaType.APPLICATION_JSON_VALUE)
public class MaritalStatusResource {

    private final MaritalStatusService maritalStatusService;

    public MaritalStatusResource(final MaritalStatusService maritalStatusService) {
        this.maritalStatusService = maritalStatusService;
    }

    @GetMapping
    public ResponseEntity<List<MaritalStatusDTO>> getAllMaritalStatuses() {
        return ResponseEntity.ok(maritalStatusService.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<MaritalStatusDTO> getMaritalStatus(
            @PathVariable(name = "id") final Long id) {
        return ResponseEntity.ok(maritalStatusService.get(id));
    }

    @PostMapping
    public ResponseEntity<Long> createMaritalStatus(
            @RequestBody @Valid final MaritalStatusDTO maritalStatusDTO) {
        final Long createdId = maritalStatusService.create(maritalStatusDTO);
        return new ResponseEntity<>(createdId, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Long> updateMaritalStatus(@PathVariable(name = "id") final Long id,
            @RequestBody @Valid final MaritalStatusDTO maritalStatusDTO) {
        maritalStatusService.update(id, maritalStatusDTO);
        return ResponseEntity.ok(id);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteMaritalStatus(@PathVariable(name = "id") final Long id) {
        maritalStatusService.delete(id);
        return ResponseEntity.noContent().build();
    }

}

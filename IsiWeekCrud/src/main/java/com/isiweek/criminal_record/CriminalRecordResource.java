package com.isiweek.criminal_record;

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
@RequestMapping(value = "/api/criminalRecords", produces = MediaType.APPLICATION_JSON_VALUE)
public class CriminalRecordResource {

    private final CriminalRecordService criminalRecordService;

    public CriminalRecordResource(final CriminalRecordService criminalRecordService) {
        this.criminalRecordService = criminalRecordService;
    }

    @GetMapping
    public ResponseEntity<List<CriminalRecord>> getAllCriminalRecords() {
        return ResponseEntity.ok(criminalRecordService.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<CriminalRecord> getCriminalRecord(
            @PathVariable(name = "id") final Long id) {
        return ResponseEntity.ok(criminalRecordService.get(id).get());
    }

    @PostMapping
    public ResponseEntity<Long> createCriminalRecord(
            @RequestBody @Valid final CriminalRecord criminalRecordDTO) {
        final Long createdId = criminalRecordService.create(criminalRecordDTO);
        return new ResponseEntity<>(createdId, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Long> updateCriminalRecord(@PathVariable(name = "id") final Long id,
            @RequestBody @Valid final CriminalRecord criminalRecordDTO) {
        criminalRecordService.update(id, criminalRecordDTO);
        return ResponseEntity.ok(id);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCriminalRecord(@PathVariable(name = "id") final Long id) {
        criminalRecordService.delete(id);
        return ResponseEntity.noContent().build();
    }

}
package com.isiweek.criminal_record;

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
@RequestMapping(value = "/api/criminalRecords", produces = MediaType.APPLICATION_JSON_VALUE)
public class CriminalRecordResource {

    private final CriminalRecordService criminalRecordService;

    public CriminalRecordResource(final CriminalRecordService criminalRecordService) {
        this.criminalRecordService = criminalRecordService;
    }

    @GetMapping
    public ResponseEntity<List<CriminalRecordDTO>> getAllCriminalRecords() {
        return ResponseEntity.ok(criminalRecordService.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<CriminalRecordDTO> getCriminalRecord(
            @PathVariable(name = "id") final Long id) {
        return ResponseEntity.ok(criminalRecordService.get(id));
    }

    @PostMapping
    public ResponseEntity<Long> createCriminalRecord(
            @RequestBody @Valid final CriminalRecordDTO criminalRecordDTO) {
        final Long createdId = criminalRecordService.create(criminalRecordDTO);
        return new ResponseEntity<>(createdId, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Long> updateCriminalRecord(@PathVariable(name = "id") final Long id,
            @RequestBody @Valid final CriminalRecordDTO criminalRecordDTO) {
        criminalRecordService.update(id, criminalRecordDTO);
        return ResponseEntity.ok(id);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCriminalRecord(@PathVariable(name = "id") final Long id) {
        final ReferencedWarning referencedWarning = criminalRecordService.getReferencedWarning(id);
        if (referencedWarning != null) {
            throw new ReferencedException(referencedWarning);
        }
        criminalRecordService.delete(id);
        return ResponseEntity.noContent().build();
    }

}

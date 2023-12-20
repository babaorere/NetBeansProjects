package com.isiweekloan.controller;

import com.isiweekloan.entity.CriminalRecordEntity;
import com.isiweekloan.service.CriminalRecordService;
import com.isiweekloan.exception.ResourceNotFoundException;
import jakarta.ws.rs.BadRequestException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/criminal-records")
public class CriminalRecordController {

    @Autowired
    private CriminalRecordService criminalRecordService;

    @GetMapping
    public ResponseEntity<List<CriminalRecordEntity>> getAllCriminalRecords() {
        List<CriminalRecordEntity> criminalRecords = criminalRecordService.findAllCriminalRecords();
        if (criminalRecords.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(criminalRecords);
    }

    @GetMapping("/{id}")
    public ResponseEntity<CriminalRecordEntity> getCriminalRecordById(@PathVariable Long id) throws ResourceNotFoundException {
        CriminalRecordEntity criminalRecord = criminalRecordService.findCriminalRecordById(id);
        if (criminalRecord == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(criminalRecord);
    }

    @PostMapping
    public ResponseEntity<CriminalRecordEntity> createCriminalRecord(@RequestBody CriminalRecordEntity criminalRecord) {
        if (criminalRecord.getName() == null || criminalRecord.getName().isEmpty()) {
            throw new BadRequestException("The name field is required.");
        }
        if (criminalRecord.getDescription() == null || criminalRecord.getDescription().isEmpty()) {
            throw new BadRequestException("The description field is required.");
        }
        CriminalRecordEntity createdCriminalRecord = criminalRecordService.createCriminalRecord(criminalRecord);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdCriminalRecord);
    }

    @PutMapping("/{id}")
    public ResponseEntity<CriminalRecordEntity> updateCriminalRecord(@PathVariable Long id, @RequestBody CriminalRecordEntity criminalRecord) throws ResourceNotFoundException {
        if (criminalRecord.getId() != id) {
            throw new BadRequestException("The id field does not match the resource being updated.");
        }
        if (criminalRecord.getName() == null || criminalRecord.getName().isEmpty()) {
            throw new BadRequestException("The name field is required.");
        }
        if (criminalRecord.getDescription() == null || criminalRecord.getDescription().isEmpty()) {
            throw new BadRequestException("The description field is required.");
        }
        CriminalRecordEntity updatedCriminalRecord = criminalRecordService.updateCriminalRecord(id, criminalRecord);
        if (updatedCriminalRecord == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(updatedCriminalRecord);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCriminalRecord(@PathVariable Long id) throws ResourceNotFoundException {
        criminalRecordService.deleteCriminalRecord(id);
        return ResponseEntity.noContent().build();
    }

    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<Void> handleResourceNotFoundException(ResourceNotFoundException e) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
    }

    @ExceptionHandler(BadRequestException.class)
    public ResponseEntity<Void> handleBadRequestException(BadRequestException e) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
    }
}

package com.isiweekloan.controller;

import com.isiweekloan.entity.CriminalRecordEntity;
import com.isiweekloan.exception.BadRequestException;
import com.isiweekloan.exception.ResourceNotFoundException;
import com.isiweekloan.service.CriminalRecordService;
import java.util.List;
import java.util.Objects;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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
    public ResponseEntity<CriminalRecordEntity> createCriminalRecord(@RequestBody CriminalRecordEntity criminalRecord) throws BadRequestException {
        if (criminalRecord.getName() == null || criminalRecord.getName().isEmpty()) {
            throw new BadRequestException("The name field is required.");
        }
        if (criminalRecord.getDescription() == null || criminalRecord.getDescription().isEmpty()) {
            throw new BadRequestException("The description field is required.");
        }
        CriminalRecordEntity createdCriminalRecord = criminalRecordService.createCriminalRecord(criminalRecord);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdCriminalRecord);
    }

    // Otros métodos del controlador...
    @PutMapping("/{id}")
    public ResponseEntity<CriminalRecordEntity> updateCriminalRecord(@PathVariable Long id, @RequestBody CriminalRecordEntity criminalRecord) {
        try {
            if (!Objects.equals(criminalRecord.getId(), id)) {
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
        } catch (BadRequestException e) {
            // Manejar la excepción de BadRequest
            return ResponseEntity.badRequest().build();
        } catch (ResourceNotFoundException e) {
            // Manejar la excepción de ResourceNotFound
            return ResponseEntity.notFound().build();
        } catch (Exception e) {
            // Manejar otras excepciones no previstas
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }

        // Otros manejadores de excepciones si es necesario...
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCriminalRecord(@PathVariable Long id) throws ResourceNotFoundException {
        criminalRecordService.deleteCriminalRecord(id);
        return ResponseEntity.noContent().build();
    }

    @ExceptionHandler(BadRequestException.class)
    public ResponseEntity<String> handleBadRequestException(BadRequestException e) {
        // Puedes personalizar la respuesta de BadRequest
        return ResponseEntity.badRequest().body(e.getMessage());
    }

    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<Void> handleResourceNotFoundException(ResourceNotFoundException e) {
        // Puedes personalizar la respuesta de ResourceNotFound
        return ResponseEntity.notFound().build();
    }
}

package com.isiweekloan.controller;

import com.isiweekloan.entity.EmployeeStatusEntity;
import com.isiweekloan.repository.EmployeeStatusRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/employee-status")
public class EmployeeStatusController {

    private final EmployeeStatusRepository employeeStatusRepository;

    @Autowired
    public EmployeeStatusController(EmployeeStatusRepository employeeStatusRepository) {
        this.employeeStatusRepository = employeeStatusRepository;
    }

    @GetMapping
    public ResponseEntity<List<EmployeeStatusEntity>> getAllEmployeeStatus() {
        return ResponseEntity.ok(employeeStatusRepository.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<EmployeeStatusEntity> getEmployeeStatusById(@PathVariable Long id) {
        Optional<EmployeeStatusEntity> optionalEmployeeStatus = employeeStatusRepository.findById(id);
        return optionalEmployeeStatus.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<EmployeeStatusEntity> createEmployeeStatus(@RequestBody EmployeeStatusEntity employeeStatusEntity) {
        validateEmployeeStatus(employeeStatusEntity);
        EmployeeStatusEntity createdEmployeeStatus = employeeStatusRepository.save(employeeStatusEntity);
        return ResponseEntity.status(201).body(createdEmployeeStatus);
    }

    @PutMapping("/{id}")
    public ResponseEntity<EmployeeStatusEntity> updateEmployeeStatus(@PathVariable Long id, @RequestBody EmployeeStatusEntity employeeStatusEntity) {
        Optional<EmployeeStatusEntity> optionalExistingEmployeeStatus = employeeStatusRepository.findById(id);

        return optionalExistingEmployeeStatus.map(existingEmployeeStatus -> {
            validateEmployeeStatus(employeeStatusEntity);
            // Update fields if needed
            existingEmployeeStatus.setName(employeeStatusEntity.getName());
            existingEmployeeStatus.setDescription(employeeStatusEntity.getDescription());
            return ResponseEntity.ok(employeeStatusRepository.save(existingEmployeeStatus));
        }).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteEmployeeStatus(@PathVariable Long id) {
        if (employeeStatusRepository.existsById(id)) {
            employeeStatusRepository.deleteById(id);
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    private void validateEmployeeStatus(EmployeeStatusEntity employeeStatusEntity) {
        if (employeeStatusEntity.getName() == null || employeeStatusEntity.getDescription() == null) {
            throw new IllegalArgumentException("Required attributes cannot be null");
        }

        // Additional validation if needed
    }

    // Exception handling
    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<String> handleIllegalArgumentException(IllegalArgumentException e) {
        return ResponseEntity.badRequest().body(e.getMessage());
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<String> handleException(Exception e) {
        return ResponseEntity.status(500).body("An error occurred");
    }
}

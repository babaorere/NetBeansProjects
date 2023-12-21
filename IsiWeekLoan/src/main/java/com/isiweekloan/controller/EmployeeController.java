package com.isiweekloan.controller;

import com.isiweekloan.entity.EmployeeEntity;
import com.isiweekloan.repository.EmployeeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.HttpStatus;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/employees")
public class EmployeeController {

    private final EmployeeRepository employeeRepository;

    @Autowired
    public EmployeeController(EmployeeRepository employeeRepository) {
        this.employeeRepository = employeeRepository;
    }

    @GetMapping
    public ResponseEntity<List<EmployeeEntity>> getAllEmployees() {
        return ResponseEntity.ok(employeeRepository.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<EmployeeEntity> getEmployeeById(@PathVariable Long id) {
        Optional<EmployeeEntity> optionalEmployee = employeeRepository.findById(id);
        return optionalEmployee.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<EmployeeEntity> createEmployee(@RequestBody EmployeeEntity employeeEntity) {
        validateEmployee(employeeEntity);
        EmployeeEntity createdEmployee = employeeRepository.save(employeeEntity);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdEmployee);
    }

    @PutMapping("/{id}")
    public ResponseEntity<EmployeeEntity> updateEmployee(@PathVariable Long id, @RequestBody EmployeeEntity employeeEntity) {
        Optional<EmployeeEntity> optionalExistingEmployee = employeeRepository.findById(id);

        return optionalExistingEmployee.map(existingEmployee -> {
            validateEmployee(employeeEntity);
            // Update fields if needed
            existingEmployee.setDateOfHire(employeeEntity.getDateOfHire());
            existingEmployee.setSalary(employeeEntity.getSalary());
            existingEmployee.setBenefits(employeeEntity.getBenefits());
            existingEmployee.setContactInformation(employeeEntity.getContactInformation());
            existingEmployee.setEducation(employeeEntity.getEducation());
            existingEmployee.setSkills(employeeEntity.getSkills());
            existingEmployee.setPerformanceReviews(employeeEntity.getPerformanceReviews());
            return ResponseEntity.ok(employeeRepository.save(existingEmployee));
        }).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteEmployee(@PathVariable Long id) {
        if (employeeRepository.existsById(id)) {
            employeeRepository.deleteById(id);
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    private void validateEmployee(EmployeeEntity employeeEntity) {
        if (employeeEntity.getDateOfHire() == null || employeeEntity.getSalary() == null
                || employeeEntity.getBenefits() == null || employeeEntity.getContactInformation() == null
                || employeeEntity.getEducation() == null || employeeEntity.getSkills() == null
                || employeeEntity.getPerformanceReviews() == null) {
            throw new IllegalArgumentException("Required attributes cannot be null");
        }

        // Additional validation if needed
    }

    // Exception handling
    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<String> handleIllegalArgumentException(IllegalArgumentException e) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<String> handleException(Exception e) {
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("An error occurred");
    }
}

package com.isiweekloan.service;

import com.isiweekloan.entity.EmployeeEntity;
import com.isiweekloan.repository.EmployeeRepository;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class EmployeeService {

    private final EmployeeRepository employeeRepository;

    @Autowired
    public EmployeeService(EmployeeRepository employeeRepository) {
        this.employeeRepository = employeeRepository;
    }

    /**
     * Retrieves all Employee entities.
     *
     * @return List of EmployeeEntity
     */
    @Transactional(readOnly = true)
    public List<EmployeeEntity> getAllEmployees() {
        return employeeRepository.findAll();
    }

    /**
     * Retrieves an Employee entity by its ID.
     *
     * @param id Employee ID
     * @return ResponseEntity with EmployeeEntity if found, or 404 Not Found if not found
     */
    @Transactional(readOnly = true)
    public ResponseEntity<EmployeeEntity> getEmployeeById(Long id) {
        Optional<EmployeeEntity> optionalEmployee = employeeRepository.findById(id);
        return optionalEmployee.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    /**
     * Creates a new Employee entity.
     *
     * @param employeeEntity EmployeeEntity to be created
     * @return ResponseEntity with created EmployeeEntity
     */
    @Transactional
    public ResponseEntity<EmployeeEntity> createEmployee(EmployeeEntity employeeEntity) {
        validateEmployee(employeeEntity);
        EmployeeEntity createdEmployee = employeeRepository.save(employeeEntity);
        return ResponseEntity.status(201).body(createdEmployee);
    }

    /**
     * Updates an existing Employee entity by its ID.
     *
     * @param id Employee ID
     * @param employeeEntity Updated EmployeeEntity
     * @return ResponseEntity with updated EmployeeEntity if found, or 404 Not Found if not found
     */
    @Transactional
    public ResponseEntity<EmployeeEntity> updateEmployee(Long id, EmployeeEntity employeeEntity) {
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

    /**
     * Deletes an Employee entity by its ID.
     *
     * @param id Employee ID
     * @return ResponseEntity with no content if deleted successfully, or 404 Not Found if not found
     */
    @Transactional
    public ResponseEntity<Void> deleteEmployee(Long id) {
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
}

package com.isiweekloan.service;

import com.isiweekloan.entity.EmployeeStatusEntity;
import com.isiweekloan.repository.EmployeeStatusRepository;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class EmployeeStatusService {

    private final EmployeeStatusRepository employeeStatusRepository;

    @Autowired
    public EmployeeStatusService(EmployeeStatusRepository employeeStatusRepository) {
        this.employeeStatusRepository = employeeStatusRepository;
    }

    /**
     * Retrieves all EmployeeStatus entities.
     *
     * @return List of EmployeeStatusEntity
     */
    @Transactional(readOnly = true)
    public List<EmployeeStatusEntity> getAllEmployeeStatus() {
        return employeeStatusRepository.findAll();
    }

    /**
     * Retrieves an EmployeeStatus entity by its ID.
     *
     * @param id EmployeeStatus ID
     * @return ResponseEntity with EmployeeStatusEntity if found, or 404 Not Found if not found
     */
    @Transactional(readOnly = true)
    public ResponseEntity<EmployeeStatusEntity> getEmployeeStatusById(Long id) {
        Optional<EmployeeStatusEntity> optionalEmployeeStatus = employeeStatusRepository.findById(id);
        return optionalEmployeeStatus.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    /**
     * Creates a new EmployeeStatus entity.
     *
     * @param employeeStatusEntity EmployeeStatusEntity to be created
     * @return ResponseEntity with created EmployeeStatusEntity
     */
    @Transactional
    public ResponseEntity<EmployeeStatusEntity> createEmployeeStatus(EmployeeStatusEntity employeeStatusEntity) {
        validateEmployeeStatus(employeeStatusEntity);
        EmployeeStatusEntity createdEmployeeStatus = employeeStatusRepository.save(employeeStatusEntity);
        return ResponseEntity.status(201).body(createdEmployeeStatus);
    }

    /**
     * Updates an existing EmployeeStatus entity by its ID.
     *
     * @param id EmployeeStatus ID
     * @param employeeStatusEntity Updated EmployeeStatusEntity
     * @return ResponseEntity with updated EmployeeStatusEntity if found, or 404 Not Found if not found
     */
    @Transactional
    public ResponseEntity<EmployeeStatusEntity> updateEmployeeStatus(Long id, EmployeeStatusEntity employeeStatusEntity) {
        Optional<EmployeeStatusEntity> optionalExistingEmployeeStatus = employeeStatusRepository.findById(id);

        return optionalExistingEmployeeStatus.map(existingEmployeeStatus -> {
            validateEmployeeStatus(employeeStatusEntity);
            // Update fields if needed
            existingEmployeeStatus.setName(employeeStatusEntity.getName());
            existingEmployeeStatus.setDescription(employeeStatusEntity.getDescription());
            return ResponseEntity.ok(employeeStatusRepository.save(existingEmployeeStatus));
        }).orElseGet(() -> ResponseEntity.notFound().build());
    }

    /**
     * Deletes an EmployeeStatus entity by its ID.
     *
     * @param id EmployeeStatus ID
     * @return ResponseEntity with no content if deleted successfully, or 404 Not Found if not found
     */
    @Transactional
    public ResponseEntity<Void> deleteEmployeeStatus(Long id) {
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
}

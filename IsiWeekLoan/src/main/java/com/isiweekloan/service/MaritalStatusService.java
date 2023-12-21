package com.isiweekloan.service;

import com.isiweekloan.entity.MaritalStatusEntity;
import com.isiweekloan.repository.MaritalStatusRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
public class MaritalStatusService {

    private final MaritalStatusRepository maritalStatusRepository;

    @Autowired
    public MaritalStatusService(MaritalStatusRepository maritalStatusRepository) {
        this.maritalStatusRepository = maritalStatusRepository;
    }

    @Transactional(readOnly = true)
    public List<MaritalStatusEntity> getAllMaritalStatuses() {
        return maritalStatusRepository.findAll();
    }

    @Transactional(readOnly = true)
    public Optional<MaritalStatusEntity> getMaritalStatusById(Long id) {
        return maritalStatusRepository.findById(id);
    }

    @Transactional
    public MaritalStatusEntity createMaritalStatus(MaritalStatusEntity maritalStatusEntity) {
        validateMaritalStatus(maritalStatusEntity);
        return maritalStatusRepository.save(maritalStatusEntity);
    }

    @Transactional
    public Optional<MaritalStatusEntity> updateMaritalStatus(Long id, MaritalStatusEntity maritalStatusEntity) {
        Objects.requireNonNull(id, "Marital Status ID cannot be null");
        validateMaritalStatus(maritalStatusEntity);

        return maritalStatusRepository.findById(id)
                .map(existingMaritalStatus -> {
                    // Update fields as needed
                    existingMaritalStatus.setName(maritalStatusEntity.getName());
                    existingMaritalStatus.setDescription(maritalStatusEntity.getDescription());
                    // Update other fields similarly

                    return maritalStatusRepository.save(existingMaritalStatus);
                });
    }

    @Transactional
    public boolean deleteMaritalStatus(Long id) {
        Objects.requireNonNull(id, "Marital Status ID cannot be null");

        if (maritalStatusRepository.existsById(id)) {
            maritalStatusRepository.deleteById(id);
            return true;
        } else {
            return false;
        }
    }

    private void validateMaritalStatus(MaritalStatusEntity maritalStatusEntity) {
        // Implement validation logic based on your requirements
        // For example, check for null values, required attributes, etc.
    }
}

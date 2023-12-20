package com.isiweekloan.security.service;

import com.isiweekloan.entity.CriminalRecordEntity;
import com.isiweekloan.entity.PersonEntity;
import com.isiweekloan.repository.CriminalRecordRepository;
import jakarta.persistence.EntityNotFoundException;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.Valid;
import jakarta.validation.ValidationException;
import jakarta.validation.Validator;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class CriminalRecordService {

    private final Logger LOGGER = LoggerFactory.getLogger(CriminalRecordService.class);

    @Autowired
    private Validator validator;

    @Autowired
    private CriminalRecordRepository criminalRecordRepository;

    @Transactional
    public CriminalRecordEntity saveCriminalRecord(@Valid CriminalRecordEntity criminalRecord) {
        validateEntity(criminalRecord); // Separate method for cleaner code

        try {
            return criminalRecordRepository.save(criminalRecord);
        } catch (DataIntegrityViolationException e) {
            throw new ValidationException("Criminal record with same name already exists.", e);
        }
    }

    @Transactional
    public CriminalRecordEntity updateCriminalRecord(@Valid CriminalRecordEntity criminalRecord) {
        validateEntity(criminalRecord); // Reuse validation method

        Long id = criminalRecord.getId();
        CriminalRecordEntity existingRecord = findById(id);

        // Update specific fields instead of replacing the entire entity
        existingRecord.setName(criminalRecord.getName());
        existingRecord.setDescription(criminalRecord.getDescription());

        return criminalRecordRepository.save(existingRecord);
    }

    @Transactional
    public void deleteCriminalRecord(Long id) {
        Optional<CriminalRecordEntity> record = criminalRecordRepository.findById(id);
        if (record.isPresent()) {
            // Handle associated Person entities before deleting
            List<PersonEntity> linkedPersons = (List<PersonEntity>) record.get().getPersonEntityCollection();
            // TODO: Implement logic to handle linked persons (e.g., detach, delete)

            criminalRecordRepository.deleteById(id);
            LOGGER.info("Deleted criminal record with ID {}", id);
        } else {
            throw new EntityNotFoundException("Criminal record not found with ID " + id);
        }
    }

    public CriminalRecordEntity findById(Long id) {
        Optional<CriminalRecordEntity> record = criminalRecordRepository.findById(id);
        return record.orElseThrow(() -> new EntityNotFoundException("Criminal record not found with ID " + id));
    }

    public List<CriminalRecordEntity> findAllCriminalRecords() {
        return criminalRecordRepository.findAll();
    }

    private void validateEntity(CriminalRecordEntity criminalRecord) {
        Set<ConstraintViolation<CriminalRecordEntity>> violations = validator.validate(criminalRecord);
        if (!violations.isEmpty()) {
            throw new ValidationException((Throwable) violations);
        }
    }
}

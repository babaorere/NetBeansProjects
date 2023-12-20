package com.isiweekloan.service;

import com.isiweekloan.entity.CriminalRecordEntity;
import com.isiweekloan.exception.ResourceNotFoundException;
import com.isiweekloan.repository.CriminalRecordRepository;
import jakarta.ws.rs.BadRequestException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CriminalRecordService {

    @Autowired
    private CriminalRecordRepository criminalRecordRepository;

    public List<CriminalRecordEntity> findAllCriminalRecords() {
        return criminalRecordRepository.findAll();
    }

    public CriminalRecordEntity findCriminalRecordById(Long id) throws ResourceNotFoundException {
        Optional<CriminalRecordEntity> criminalRecord = criminalRecordRepository.findById(id);
        if (!criminalRecord.isPresent()) {
            throw new ResourceNotFoundException("Criminal record with ID " + id + " not found.");
        }
        return criminalRecord.get();
    }

    public CriminalRecordEntity createCriminalRecord(CriminalRecordEntity criminalRecord) {
        validateRequiredFields(criminalRecord);
        return criminalRecordRepository.save(criminalRecord);
    }

    public CriminalRecordEntity updateCriminalRecord(Long id, CriminalRecordEntity criminalRecord) throws ResourceNotFoundException {
        if (!criminalRecord.getId().equals(id)) {
            throw new BadRequestException("ID in request body does not match ID in path variable.");
        }
        validateRequiredFields(criminalRecord);
        Optional<CriminalRecordEntity> existingRecord = criminalRecordRepository.findById(id);
        if (!existingRecord.isPresent()) {
            throw new ResourceNotFoundException("Criminal record with ID " + id + " not found.");
        }
        existingRecord.get().setName(criminalRecord.getName());
        existingRecord.get().setDescription(criminalRecord.getDescription());
        return criminalRecordRepository.save(existingRecord.get());
    }

    public void deleteCriminalRecord(Long id) throws ResourceNotFoundException {
        Optional<CriminalRecordEntity> criminalRecord = criminalRecordRepository.findById(id);
        if (!criminalRecord.isPresent()) {
            throw new ResourceNotFoundException("Criminal record with ID " + id + " not found.");
        }
        criminalRecordRepository.delete(criminalRecord.get());
    }

    private void validateRequiredFields(CriminalRecordEntity criminalRecord) {
        if (criminalRecord.getName() == null || criminalRecord.getName().isEmpty()) {
            throw new BadRequestException("The name field is required.");
        }
        if (criminalRecord.getDescription() == null || criminalRecord.getDescription().isEmpty()) {
            throw new BadRequestException("The description field is required.");
        }
    }
}
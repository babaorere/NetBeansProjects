package com.isiweekloan.service;

import com.isiweekloan.entity.LoanTypeEntity;
import com.isiweekloan.repository.LoanTypeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
public class LoanTypeService {

    private final LoanTypeRepository loanTypeRepository;

    @Autowired
    public LoanTypeService(LoanTypeRepository loanTypeRepository) {
        this.loanTypeRepository = loanTypeRepository;
    }

    @Transactional(readOnly = true)
    public List<LoanTypeEntity> getAllLoanTypes() {
        return loanTypeRepository.findAll();
    }

    @Transactional(readOnly = true)
    public Optional<LoanTypeEntity> getLoanTypeById(Long id) {
        return loanTypeRepository.findById(id);
    }

    @Transactional
    public LoanTypeEntity createLoanType(LoanTypeEntity loanTypeEntity) {
        validateLoanType(loanTypeEntity);
        return loanTypeRepository.save(loanTypeEntity);
    }

    @Transactional
    public Optional<LoanTypeEntity> updateLoanType(Long id, LoanTypeEntity loanTypeEntity) {
        Objects.requireNonNull(id, "Loan Type ID cannot be null");
        validateLoanType(loanTypeEntity);

        return loanTypeRepository.findById(id)
                .map(existingLoanType -> {
                    // Update fields as needed
                    existingLoanType.setName(loanTypeEntity.getName());
                    existingLoanType.setDescription(loanTypeEntity.getDescription());
                    // Update other fields similarly

                    return loanTypeRepository.save(existingLoanType);
                });
    }

    @Transactional
    public boolean deleteLoanType(Long id) {
        Objects.requireNonNull(id, "Loan Type ID cannot be null");

        if (loanTypeRepository.existsById(id)) {
            loanTypeRepository.deleteById(id);
            return true;
        } else {
            return false;
        }
    }

    private void validateLoanType(LoanTypeEntity loanTypeEntity) {
        // Implement validation logic based on your requirements
        // For example, check for null values, required attributes, etc.
    }
}

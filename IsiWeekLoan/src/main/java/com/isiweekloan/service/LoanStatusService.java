package com.isiweekloan.service;

import com.isiweekloan.entity.LoanStatusEntity;
import com.isiweekloan.repository.LoanStatusRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
public class LoanStatusService {

    private final LoanStatusRepository loanStatusRepository;

    @Autowired
    public LoanStatusService(LoanStatusRepository loanStatusRepository) {
        this.loanStatusRepository = loanStatusRepository;
    }

    @Transactional(readOnly = true)
    public List<LoanStatusEntity> getAllLoanStatuses() {
        return loanStatusRepository.findAll();
    }

    @Transactional(readOnly = true)
    public Optional<LoanStatusEntity> getLoanStatusById(Long id) {
        return loanStatusRepository.findById(id);
    }

    @Transactional
    public LoanStatusEntity createLoanStatus(LoanStatusEntity loanStatusEntity) {
        validateLoanStatus(loanStatusEntity);
        return loanStatusRepository.save(loanStatusEntity);
    }

    @Transactional
    public Optional<LoanStatusEntity> updateLoanStatus(Long id, LoanStatusEntity loanStatusEntity) {
        Objects.requireNonNull(id, "Loan Status ID cannot be null");
        validateLoanStatus(loanStatusEntity);

        return loanStatusRepository.findById(id)
                .map(existingLoanStatus -> {
                    // Update fields as needed
                    existingLoanStatus.setName(loanStatusEntity.getName());
                    existingLoanStatus.setDescription(loanStatusEntity.getDescription());
                    // Update other fields similarly

                    return loanStatusRepository.save(existingLoanStatus);
                });
    }

    @Transactional
    public boolean deleteLoanStatus(Long id) {
        Objects.requireNonNull(id, "Loan Status ID cannot be null");

        if (loanStatusRepository.existsById(id)) {
            loanStatusRepository.deleteById(id);
            return true;
        } else {
            return false;
        }
    }

    private void validateLoanStatus(LoanStatusEntity loanStatusEntity) {
        // Implement validation logic based on your requirements
        // For example, check for null values, required attributes, etc.
    }
}

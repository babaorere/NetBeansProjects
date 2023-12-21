package com.isiweekloan.service;

import com.isiweekloan.entity.LoanCollectorStatusEntity;
import com.isiweekloan.repository.LoanCollectorStatusRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
public class LoanCollectorStatusService {

    private final LoanCollectorStatusRepository loanCollectorStatusRepository;

    @Autowired
    public LoanCollectorStatusService(LoanCollectorStatusRepository loanCollectorStatusRepository) {
        this.loanCollectorStatusRepository = loanCollectorStatusRepository;
    }

    @Transactional(readOnly = true)
    public List<LoanCollectorStatusEntity> getAllLoanCollectorStatus() {
        return loanCollectorStatusRepository.findAll();
    }

    @Transactional(readOnly = true)
    public Optional<LoanCollectorStatusEntity> getLoanCollectorStatusById(Long id) {
        return loanCollectorStatusRepository.findById(id);
    }

    @Transactional
    public LoanCollectorStatusEntity createLoanCollectorStatus(LoanCollectorStatusEntity loanCollectorStatusEntity) {
        validateLoanCollectorStatus(loanCollectorStatusEntity);
        return loanCollectorStatusRepository.save(loanCollectorStatusEntity);
    }

    @Transactional
    public Optional<LoanCollectorStatusEntity> updateLoanCollectorStatus(Long id, LoanCollectorStatusEntity loanCollectorStatusEntity) {
        Objects.requireNonNull(id, "Loan Collector Status ID cannot be null");
        validateLoanCollectorStatus(loanCollectorStatusEntity);

        return loanCollectorStatusRepository.findById(id)
                .map(existingLoanCollectorStatus -> {
                    // Update fields as needed
                    existingLoanCollectorStatus.setName(loanCollectorStatusEntity.getName());
                    existingLoanCollectorStatus.setDescription(loanCollectorStatusEntity.getDescription());

                    return loanCollectorStatusRepository.save(existingLoanCollectorStatus);
                });
    }

    @Transactional
    public boolean deleteLoanCollectorStatus(Long id) {
        Objects.requireNonNull(id, "Loan Collector Status ID cannot be null");

        if (loanCollectorStatusRepository.existsById(id)) {
            loanCollectorStatusRepository.deleteById(id);
            return true;
        } else {
            return false;
        }
    }

    private void validateLoanCollectorStatus(LoanCollectorStatusEntity loanCollectorStatusEntity) {
        Objects.requireNonNull(loanCollectorStatusEntity.getName(), "Name cannot be null");
        Objects.requireNonNull(loanCollectorStatusEntity.getDescription(), "Description cannot be null");

        // Additional validation if needed
    }
}

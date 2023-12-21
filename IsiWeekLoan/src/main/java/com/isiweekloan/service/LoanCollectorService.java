package com.isiweekloan.service;

import com.isiweekloan.entity.LoanCollectorEntity;
import com.isiweekloan.repository.LoanCollectorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
public class LoanCollectorService {

    private final LoanCollectorRepository loanCollectorRepository;

    @Autowired
    public LoanCollectorService(LoanCollectorRepository loanCollectorRepository) {
        this.loanCollectorRepository = loanCollectorRepository;
    }

    @Transactional(readOnly = true)
    public List<LoanCollectorEntity> getAllLoanCollectors() {
        return loanCollectorRepository.findAll();
    }

    @Transactional(readOnly = true)
    public Optional<LoanCollectorEntity> getLoanCollectorById(Long id) {
        return loanCollectorRepository.findById(id);
    }

    @Transactional
    public LoanCollectorEntity createLoanCollector(LoanCollectorEntity loanCollectorEntity) {
        validateLoanCollector(loanCollectorEntity);
        return loanCollectorRepository.save(loanCollectorEntity);
    }

    @Transactional
    public Optional<LoanCollectorEntity> updateLoanCollector(Long id, LoanCollectorEntity loanCollectorEntity) {
        Objects.requireNonNull(id, "Loan Collector ID cannot be null");
        validateLoanCollector(loanCollectorEntity);

        return loanCollectorRepository.findById(id)
                .map(existingLoanCollector -> {
                    // Update fields as needed
                    existingLoanCollector.setIdPerson(loanCollectorEntity.getIdPerson());
                    existingLoanCollector.setIdLcStatus(loanCollectorEntity.getIdLcStatus());

                    return loanCollectorRepository.save(existingLoanCollector);
                });
    }

    @Transactional
    public boolean deleteLoanCollector(Long id) {
        Objects.requireNonNull(id, "Loan Collector ID cannot be null");

        if (loanCollectorRepository.existsById(id)) {
            loanCollectorRepository.deleteById(id);
            return true;
        } else {
            return false;
        }
    }

    private void validateLoanCollector(LoanCollectorEntity loanCollectorEntity) {
        Objects.requireNonNull(loanCollectorEntity.getIdPerson(), "Person ID cannot be null");
        Objects.requireNonNull(loanCollectorEntity.getIdLcStatus(), "Loan Collector Status ID cannot be null");

        // Additional validation if needed
    }
}

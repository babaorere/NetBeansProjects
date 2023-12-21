package com.isiweekloan.service;

import com.isiweekloan.entity.LoanContractEntity;
import com.isiweekloan.repository.LoanContractRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
public class LoanContractService {

    private final LoanContractRepository loanContractRepository;

    @Autowired
    public LoanContractService(LoanContractRepository loanContractRepository) {
        this.loanContractRepository = loanContractRepository;
    }

    @Transactional(readOnly = true)
    public List<LoanContractEntity> getAllLoanContracts() {
        return loanContractRepository.findAll();
    }

    @Transactional(readOnly = true)
    public Optional<LoanContractEntity> getLoanContractById(Long id) {
        return loanContractRepository.findById(id);
    }

    @Transactional
    public LoanContractEntity createLoanContract(LoanContractEntity loanContractEntity) {
        validateLoanContract(loanContractEntity);
        return loanContractRepository.save(loanContractEntity);
    }

    @Transactional
    public Optional<LoanContractEntity> updateLoanContract(Long id, LoanContractEntity loanContractEntity) {
        Objects.requireNonNull(id, "Loan Contract ID cannot be null");
        validateLoanContract(loanContractEntity);

        return loanContractRepository.findById(id)
                .map(existingLoanContract -> {
                    // Update fields as needed
                    existingLoanContract.setLoanTerm(loanContractEntity.getLoanTerm());
                    existingLoanContract.setDate(loanContractEntity.getDate());
                    existingLoanContract.setDateOfMaturity(loanContractEntity.getDateOfMaturity());
                    // Update other fields similarly

                    return loanContractRepository.save(existingLoanContract);
                });
    }

    @Transactional
    public boolean deleteLoanContract(Long id) {
        Objects.requireNonNull(id, "Loan Contract ID cannot be null");

        if (loanContractRepository.existsById(id)) {
            loanContractRepository.deleteById(id);
            return true;
        } else {
            return false;
        }
    }

    private void validateLoanContract(LoanContractEntity loanContractEntity) {
        // Implement validation logic based on your requirements
        // For example, check for null values, required attributes, etc.
    }
}

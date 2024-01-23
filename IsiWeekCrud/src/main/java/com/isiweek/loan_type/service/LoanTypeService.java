package com.isiweek.loan_type.service;

import com.isiweek.loan_contract.domain.LoanContract;
import com.isiweek.loan_contract.repos.LoanContractRepository;
import com.isiweek.loan_type.domain.LoanType;
import com.isiweek.loan_type.model.LoanTypeDTO;
import com.isiweek.loan_type.repos.LoanTypeRepository;
import com.isiweek.util.NotFoundException;
import com.isiweek.util.WebUtils;
import java.util.List;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

@Service
public class LoanTypeService {

    private final LoanTypeRepository loanTypeRepository;
    private final LoanContractRepository loanContractRepository;

    public LoanTypeService(final LoanTypeRepository loanTypeRepository,
            final LoanContractRepository loanContractRepository) {
        this.loanTypeRepository = loanTypeRepository;
        this.loanContractRepository = loanContractRepository;
    }

    public List<LoanTypeDTO> findAll() {
        final List<LoanType> loanTypes = loanTypeRepository.findAll(Sort.by("id"));
        return loanTypes.stream()
                .map(loanType -> mapToDTO(loanType, new LoanTypeDTO()))
                .toList();
    }

    public LoanTypeDTO get(final Long id) {
        return loanTypeRepository.findById(id)
                .map(loanType -> mapToDTO(loanType, new LoanTypeDTO()))
                .orElseThrow(NotFoundException::new);
    }

    public Long create(final LoanTypeDTO loanTypeDTO) {
        final LoanType loanType = new LoanType();
        mapToEntity(loanTypeDTO, loanType);
        return loanTypeRepository.save(loanType).getId();
    }

    public void update(final Long id, final LoanTypeDTO loanTypeDTO) {
        final LoanType loanType = loanTypeRepository.findById(id)
                .orElseThrow(NotFoundException::new);
        mapToEntity(loanTypeDTO, loanType);
        loanTypeRepository.save(loanType);
    }

    public void delete(final Long id) {
        loanTypeRepository.deleteById(id);
    }

    private LoanTypeDTO mapToDTO(final LoanType loanType, final LoanTypeDTO loanTypeDTO) {
        loanTypeDTO.setId(loanType.getId());
        loanTypeDTO.setName(loanType.getName());
        loanTypeDTO.setDescription(loanType.getDescription());
        return loanTypeDTO;
    }

    private LoanType mapToEntity(final LoanTypeDTO loanTypeDTO, final LoanType loanType) {
        loanType.setName(loanTypeDTO.getName());
        loanType.setDescription(loanTypeDTO.getDescription());
        return loanType;
    }

    public boolean nameExists(final String name) {
        return loanTypeRepository.existsByNameIgnoreCase(name);
    }

    public boolean descriptionExists(final String description) {
        return loanTypeRepository.existsByDescriptionIgnoreCase(description);
    }

    public String getReferencedWarning(final Long id) {
        final LoanType loanType = loanTypeRepository.findById(id)
                .orElseThrow(NotFoundException::new);
        final LoanContract loanTypeLoanContract = loanContractRepository.findFirstByLoanType(loanType);
        if (loanTypeLoanContract != null) {
            return WebUtils.getMessage("loanType.loanContract.loanType.referenced", loanTypeLoanContract.getId());
        }

        return null;
    }

}

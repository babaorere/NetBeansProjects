package com.isiweek.loan_status;

import com.isiweek.loan_contract.LoanContract;
import com.isiweek.loan_contract.LoanContractRepository;
import com.isiweek.util.NotFoundException;
import com.isiweek.util.ReferencedWarning;
import java.util.List;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;


@Service
public class LoanStatusService {

    private final LoanStatusRepository loanStatusRepository;
    private final LoanContractRepository loanContractRepository;

    public LoanStatusService(final LoanStatusRepository loanStatusRepository,
            final LoanContractRepository loanContractRepository) {
        this.loanStatusRepository = loanStatusRepository;
        this.loanContractRepository = loanContractRepository;
    }

    public List<LoanStatusDTO> findAll() {
        final List<LoanStatus> loanStatuses = loanStatusRepository.findAll(Sort.by("id"));
        return loanStatuses.stream()
                .map(loanStatus -> mapToDTO(loanStatus, new LoanStatusDTO()))
                .toList();
    }

    public LoanStatusDTO get(final Long id) {
        return loanStatusRepository.findById(id)
                .map(loanStatus -> mapToDTO(loanStatus, new LoanStatusDTO()))
                .orElseThrow(NotFoundException::new);
    }

    public Long create(final LoanStatusDTO loanStatusDTO) {
        final LoanStatus loanStatus = new LoanStatus();
        mapToEntity(loanStatusDTO, loanStatus);
        return loanStatusRepository.save(loanStatus).getId();
    }

    public void update(final Long id, final LoanStatusDTO loanStatusDTO) {
        final LoanStatus loanStatus = loanStatusRepository.findById(id)
                .orElseThrow(NotFoundException::new);
        mapToEntity(loanStatusDTO, loanStatus);
        loanStatusRepository.save(loanStatus);
    }

    public void delete(final Long id) {
        loanStatusRepository.deleteById(id);
    }

    private LoanStatusDTO mapToDTO(final LoanStatus loanStatus, final LoanStatusDTO loanStatusDTO) {
        loanStatusDTO.setId(loanStatus.getId());
        loanStatusDTO.setDateCreated(loanStatus.getDateCreated());
        loanStatusDTO.setLastUpdated(loanStatus.getLastUpdated());
        loanStatusDTO.setName(loanStatus.getName());
        loanStatusDTO.setDescription(loanStatus.getDescription());
        return loanStatusDTO;
    }

    private LoanStatus mapToEntity(final LoanStatusDTO loanStatusDTO, final LoanStatus loanStatus) {
        loanStatus.setDateCreated(loanStatusDTO.getDateCreated());
        loanStatus.setLastUpdated(loanStatusDTO.getLastUpdated());
        loanStatus.setName(loanStatusDTO.getName());
        loanStatus.setDescription(loanStatusDTO.getDescription());
        return loanStatus;
    }

    public boolean nameExists(final String name) {
        return loanStatusRepository.existsByNameIgnoreCase(name);
    }

    public ReferencedWarning getReferencedWarning(final Long id) {
        final ReferencedWarning referencedWarning = new ReferencedWarning();
        final LoanStatus loanStatus = loanStatusRepository.findById(id)
                .orElseThrow(NotFoundException::new);
        final LoanContract loanStatusLoanContract = loanContractRepository.findFirstByLoanStatus(loanStatus);
        if (loanStatusLoanContract != null) {
            referencedWarning.setKey("loanStatus.loanContract.loanStatus.referenced");
            referencedWarning.addParam(loanStatusLoanContract.getId());
            return referencedWarning;
        }
        return null;
    }

}

package com.isiweek.loan_collector_status;

import com.isiweek.loan_collector.LoanCollector;
import com.isiweek.loan_collector.LoanCollectorRepository;
import com.isiweek.util.NotFoundException;
import com.isiweek.util.ReferencedWarning;
import java.util.List;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;


@Service
public class LoanCollectorStatusService {

    private final LoanCollectorStatusRepository loanCollectorStatusRepository;
    private final LoanCollectorRepository loanCollectorRepository;

    public LoanCollectorStatusService(
            final LoanCollectorStatusRepository loanCollectorStatusRepository,
            final LoanCollectorRepository loanCollectorRepository) {
        this.loanCollectorStatusRepository = loanCollectorStatusRepository;
        this.loanCollectorRepository = loanCollectorRepository;
    }

    public List<LoanCollectorStatusDTO> findAll() {
        final List<LoanCollectorStatus> loanCollectorStatuses = loanCollectorStatusRepository.findAll(Sort.by("id"));
        return loanCollectorStatuses.stream()
                .map(loanCollectorStatus -> mapToDTO(loanCollectorStatus, new LoanCollectorStatusDTO()))
                .toList();
    }

    public LoanCollectorStatusDTO get(final Long id) {
        return loanCollectorStatusRepository.findById(id)
                .map(loanCollectorStatus -> mapToDTO(loanCollectorStatus, new LoanCollectorStatusDTO()))
                .orElseThrow(NotFoundException::new);
    }

    public Long create(final LoanCollectorStatusDTO loanCollectorStatusDTO) {
        final LoanCollectorStatus loanCollectorStatus = new LoanCollectorStatus();
        mapToEntity(loanCollectorStatusDTO, loanCollectorStatus);
        return loanCollectorStatusRepository.save(loanCollectorStatus).getId();
    }

    public void update(final Long id, final LoanCollectorStatusDTO loanCollectorStatusDTO) {
        final LoanCollectorStatus loanCollectorStatus = loanCollectorStatusRepository.findById(id)
                .orElseThrow(NotFoundException::new);
        mapToEntity(loanCollectorStatusDTO, loanCollectorStatus);
        loanCollectorStatusRepository.save(loanCollectorStatus);
    }

    public void delete(final Long id) {
        loanCollectorStatusRepository.deleteById(id);
    }

    private LoanCollectorStatusDTO mapToDTO(final LoanCollectorStatus loanCollectorStatus,
            final LoanCollectorStatusDTO loanCollectorStatusDTO) {
        loanCollectorStatusDTO.setId(loanCollectorStatus.getId());
        loanCollectorStatusDTO.setDateCreated(loanCollectorStatus.getDateCreated());
        loanCollectorStatusDTO.setLastUpdated(loanCollectorStatus.getLastUpdated());
        loanCollectorStatusDTO.setName(loanCollectorStatus.getName());
        loanCollectorStatusDTO.setDescription(loanCollectorStatus.getDescription());
        return loanCollectorStatusDTO;
    }

    private LoanCollectorStatus mapToEntity(final LoanCollectorStatusDTO loanCollectorStatusDTO,
            final LoanCollectorStatus loanCollectorStatus) {
        loanCollectorStatus.setDateCreated(loanCollectorStatusDTO.getDateCreated());
        loanCollectorStatus.setLastUpdated(loanCollectorStatusDTO.getLastUpdated());
        loanCollectorStatus.setName(loanCollectorStatusDTO.getName());
        loanCollectorStatus.setDescription(loanCollectorStatusDTO.getDescription());
        return loanCollectorStatus;
    }

    public boolean nameExists(final String name) {
        return loanCollectorStatusRepository.existsByNameIgnoreCase(name);
    }

    public ReferencedWarning getReferencedWarning(final Long id) {
        final ReferencedWarning referencedWarning = new ReferencedWarning();
        final LoanCollectorStatus loanCollectorStatus = loanCollectorStatusRepository.findById(id)
                .orElseThrow(NotFoundException::new);
        final LoanCollector lcStatusLoanCollector = loanCollectorRepository.findFirstByLcStatus(loanCollectorStatus);
        if (lcStatusLoanCollector != null) {
            referencedWarning.setKey("loanCollectorStatus.loanCollector.lcStatus.referenced");
            referencedWarning.addParam(lcStatusLoanCollector.getId());
            return referencedWarning;
        }
        return null;
    }

}

package baba.loan_app.status;

import baba.loan_app.company.Company;
import baba.loan_app.company.CompanyRepository;
import baba.loan_app.util.NotFoundException;
import baba.loan_app.util.ReferencedWarning;
import java.util.List;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;


@Service
public class StatusService {

    private final StatusRepository statusRepository;
    private final CompanyRepository companyRepository;

    public StatusService(final StatusRepository statusRepository,
            final CompanyRepository companyRepository) {
        this.statusRepository = statusRepository;
        this.companyRepository = companyRepository;
    }

    public List<StatusDTO> findAll() {
        final List<Status> statuses = statusRepository.findAll(Sort.by("id"));
        return statuses.stream()
                .map(status -> mapToDTO(status, new StatusDTO()))
                .toList();
    }

    public StatusDTO get(final Long id) {
        return statusRepository.findById(id)
                .map(status -> mapToDTO(status, new StatusDTO()))
                .orElseThrow(NotFoundException::new);
    }

    public Long create(final StatusDTO statusDTO) {
        final Status status = new Status();
        mapToEntity(statusDTO, status);
        return statusRepository.save(status).getId();
    }

    public void update(final Long id, final StatusDTO statusDTO) {
        final Status status = statusRepository.findById(id)
                .orElseThrow(NotFoundException::new);
        mapToEntity(statusDTO, status);
        statusRepository.save(status);
    }

    public void delete(final Long id) {
        statusRepository.deleteById(id);
    }

    private StatusDTO mapToDTO(final Status status, final StatusDTO statusDTO) {
        statusDTO.setId(status.getId());
        statusDTO.setName(status.getName());
        return statusDTO;
    }

    private Status mapToEntity(final StatusDTO statusDTO, final Status status) {
        status.setName(statusDTO.getName());
        return status;
    }

    public boolean nameExists(final StatusEnum name) {
        return statusRepository.existsByName(name);
    }

    public ReferencedWarning getReferencedWarning(final Long id) {
        final ReferencedWarning referencedWarning = new ReferencedWarning();
        final Status status = statusRepository.findById(id)
                .orElseThrow(NotFoundException::new);
        final Company statusCompany = companyRepository.findFirstByStatus(status);
        if (statusCompany != null) {
            referencedWarning.setKey("status.company.status.referenced");
            referencedWarning.addParam(statusCompany.getId());
            return referencedWarning;
        }
        return null;
    }

}

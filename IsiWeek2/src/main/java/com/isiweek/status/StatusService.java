package com.isiweek.status;

import com.isiweek.company.Company;
import com.isiweek.company.CompanyRepository;
import com.isiweek.util.NotFoundException;
import com.isiweek.util.ReferencedWarning;
import java.util.List;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;


@Service
public class StatusService {

    private final StatusRepository statusEntityRepository;
    private final CompanyRepository companyRepository;

    public StatusService(final StatusRepository statusEntityRepository,
            final CompanyRepository companyRepository) {
        this.statusEntityRepository = statusEntityRepository;
        this.companyRepository = companyRepository;
    }

    public List<StatusDTO> findAll() {
        final List<Status> statusEntities = statusEntityRepository.findAll(Sort.by("id"));
        return statusEntities.stream()
                .map(statusEntity -> mapToDTO(statusEntity, new StatusDTO()))
                .toList();
    }

    public StatusDTO get(final Long id) {
        return statusEntityRepository.findById(id)
                .map(statusEntity -> mapToDTO(statusEntity, new StatusDTO()))
                .orElseThrow(NotFoundException::new);
    }

    public Long create(final StatusDTO statusEntityDTO) {
        final Status statusEntity = new Status();
        mapToEntity(statusEntityDTO, statusEntity);
        return statusEntityRepository.save(statusEntity).getId();
    }

    public void update(final Long id, final StatusDTO statusEntityDTO) {
        final Status statusEntity = statusEntityRepository.findById(id)
                .orElseThrow(NotFoundException::new);
        mapToEntity(statusEntityDTO, statusEntity);
        statusEntityRepository.save(statusEntity);
    }

    public void delete(final Long id) {
        statusEntityRepository.deleteById(id);
    }

    private StatusDTO mapToDTO(final Status statusEntity,
            final StatusDTO statusEntityDTO) {
        statusEntityDTO.setId(statusEntity.getId());
        statusEntityDTO.setName(statusEntity.getName());
        return statusEntityDTO;
    }

    private Status mapToEntity(final StatusDTO statusEntityDTO,
            final Status statusEntity) {
        statusEntity.setName(statusEntityDTO.getName());
        return statusEntity;
    }

    public boolean nameExists(final StatusEnum name) {
        return statusEntityRepository.existsByName(name);
    }

    public ReferencedWarning getReferencedWarning(final Long id) {
        final ReferencedWarning referencedWarning = new ReferencedWarning();
        final Status statusEntity = statusEntityRepository.findById(id)
                .orElseThrow(NotFoundException::new);
        final Company statusCompany = companyRepository.findFirstByStatus(statusEntity);
        if (statusCompany != null) {
            referencedWarning.setKey("statusEntity.company.status.referenced");
            referencedWarning.addParam(statusCompany.getId());
            return referencedWarning;
        }
        return null;
    }

}

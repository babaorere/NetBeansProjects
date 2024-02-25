package com.isiweek.status_entity;

import com.isiweek.company.Company;
import com.isiweek.company.CompanyRepository;
import com.isiweek.util.NotFoundException;
import com.isiweek.util.ReferencedWarning;
import java.util.List;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;


@Service
public class StatusEntityService {

    private final StatusEntityRepository statusEntityRepository;
    private final CompanyRepository companyRepository;

    public StatusEntityService(final StatusEntityRepository statusEntityRepository,
            final CompanyRepository companyRepository) {
        this.statusEntityRepository = statusEntityRepository;
        this.companyRepository = companyRepository;
    }

    public List<StatusEntityDTO> findAll() {
        final List<StatusEntity> statusEntities = statusEntityRepository.findAll(Sort.by("id"));
        return statusEntities.stream()
                .map(statusEntity -> mapToDTO(statusEntity, new StatusEntityDTO()))
                .toList();
    }

    public StatusEntityDTO get(final Long id) {
        return statusEntityRepository.findById(id)
                .map(statusEntity -> mapToDTO(statusEntity, new StatusEntityDTO()))
                .orElseThrow(NotFoundException::new);
    }

    public Long create(final StatusEntityDTO statusEntityDTO) {
        final StatusEntity statusEntity = new StatusEntity();
        mapToEntity(statusEntityDTO, statusEntity);
        return statusEntityRepository.save(statusEntity).getId();
    }

    public void update(final Long id, final StatusEntityDTO statusEntityDTO) {
        final StatusEntity statusEntity = statusEntityRepository.findById(id)
                .orElseThrow(NotFoundException::new);
        mapToEntity(statusEntityDTO, statusEntity);
        statusEntityRepository.save(statusEntity);
    }

    public void delete(final Long id) {
        statusEntityRepository.deleteById(id);
    }

    private StatusEntityDTO mapToDTO(final StatusEntity statusEntity,
            final StatusEntityDTO statusEntityDTO) {
        statusEntityDTO.setId(statusEntity.getId());
        statusEntityDTO.setName(statusEntity.getName());
        return statusEntityDTO;
    }

    private StatusEntity mapToEntity(final StatusEntityDTO statusEntityDTO,
            final StatusEntity statusEntity) {
        statusEntity.setName(statusEntityDTO.getName());
        return statusEntity;
    }

    public boolean nameExists(final StatusEnum name) {
        return statusEntityRepository.existsByName(name);
    }

    public ReferencedWarning getReferencedWarning(final Long id) {
        final ReferencedWarning referencedWarning = new ReferencedWarning();
        final StatusEntity statusEntity = statusEntityRepository.findById(id)
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

package com.isiweek.marital_status;

import com.isiweek.person.Person;
import com.isiweek.person.PersonRepository;
import com.isiweek.util.NotFoundException;
import com.isiweek.util.ReferencedWarning;
import java.util.List;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;


@Service
public class MaritalStatusService {

    private final MaritalStatusRepository maritalStatusRepository;
    private final PersonRepository personRepository;

    public MaritalStatusService(final MaritalStatusRepository maritalStatusRepository,
            final PersonRepository personRepository) {
        this.maritalStatusRepository = maritalStatusRepository;
        this.personRepository = personRepository;
    }

    public List<MaritalStatusDTO> findAll() {
        final List<MaritalStatus> maritalStatuses = maritalStatusRepository.findAll(Sort.by("id"));
        return maritalStatuses.stream()
                .map(maritalStatus -> mapToDTO(maritalStatus, new MaritalStatusDTO()))
                .toList();
    }

    public MaritalStatusDTO get(final Long id) {
        return maritalStatusRepository.findById(id)
                .map(maritalStatus -> mapToDTO(maritalStatus, new MaritalStatusDTO()))
                .orElseThrow(NotFoundException::new);
    }

    public Long create(final MaritalStatusDTO maritalStatusDTO) {
        final MaritalStatus maritalStatus = new MaritalStatus();
        mapToEntity(maritalStatusDTO, maritalStatus);
        return maritalStatusRepository.save(maritalStatus).getId();
    }

    public void update(final Long id, final MaritalStatusDTO maritalStatusDTO) {
        final MaritalStatus maritalStatus = maritalStatusRepository.findById(id)
                .orElseThrow(NotFoundException::new);
        mapToEntity(maritalStatusDTO, maritalStatus);
        maritalStatusRepository.save(maritalStatus);
    }

    public void delete(final Long id) {
        maritalStatusRepository.deleteById(id);
    }

    private MaritalStatusDTO mapToDTO(final MaritalStatus maritalStatus,
            final MaritalStatusDTO maritalStatusDTO) {
        maritalStatusDTO.setId(maritalStatus.getId());
        maritalStatusDTO.setDateCreated(maritalStatus.getDateCreated());
        maritalStatusDTO.setLastUpdated(maritalStatus.getLastUpdated());
        maritalStatusDTO.setName(maritalStatus.getName());
        maritalStatusDTO.setDescription(maritalStatus.getDescription());
        return maritalStatusDTO;
    }

    private MaritalStatus mapToEntity(final MaritalStatusDTO maritalStatusDTO,
            final MaritalStatus maritalStatus) {
        maritalStatus.setDateCreated(maritalStatusDTO.getDateCreated());
        maritalStatus.setLastUpdated(maritalStatusDTO.getLastUpdated());
        maritalStatus.setName(maritalStatusDTO.getName());
        maritalStatus.setDescription(maritalStatusDTO.getDescription());
        return maritalStatus;
    }

    public boolean nameExists(final String name) {
        return maritalStatusRepository.existsByNameIgnoreCase(name);
    }

    public ReferencedWarning getReferencedWarning(final Long id) {
        final ReferencedWarning referencedWarning = new ReferencedWarning();
        final MaritalStatus maritalStatus = maritalStatusRepository.findById(id)
                .orElseThrow(NotFoundException::new);
        final Person maritalStatusPerson = personRepository.findFirstByMaritalStatus(maritalStatus);
        if (maritalStatusPerson != null) {
            referencedWarning.setKey("maritalStatus.person.maritalStatus.referenced");
            referencedWarning.addParam(maritalStatusPerson.getId());
            return referencedWarning;
        }
        return null;
    }

}

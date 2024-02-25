package com.isiweek.loan_collector;

import com.isiweek.loan_collector_status.LoanCollectorStatus;
import com.isiweek.loan_collector_status.LoanCollectorStatusRepository;
import com.isiweek.person.Person;
import com.isiweek.person.PersonRepository;
import com.isiweek.util.NotFoundException;
import java.util.List;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;


@Service
public class LoanCollectorService {

    private final LoanCollectorRepository loanCollectorRepository;
    private final PersonRepository personRepository;
    private final LoanCollectorStatusRepository loanCollectorStatusRepository;

    public LoanCollectorService(final LoanCollectorRepository loanCollectorRepository,
            final PersonRepository personRepository,
            final LoanCollectorStatusRepository loanCollectorStatusRepository) {
        this.loanCollectorRepository = loanCollectorRepository;
        this.personRepository = personRepository;
        this.loanCollectorStatusRepository = loanCollectorStatusRepository;
    }

    public List<LoanCollectorDTO> findAll() {
        final List<LoanCollector> loanCollectors = loanCollectorRepository.findAll(Sort.by("id"));
        return loanCollectors.stream()
                .map(loanCollector -> mapToDTO(loanCollector, new LoanCollectorDTO()))
                .toList();
    }

    public LoanCollectorDTO get(final Long id) {
        return loanCollectorRepository.findById(id)
                .map(loanCollector -> mapToDTO(loanCollector, new LoanCollectorDTO()))
                .orElseThrow(NotFoundException::new);
    }

    public Long create(final LoanCollectorDTO loanCollectorDTO) {
        final LoanCollector loanCollector = new LoanCollector();
        mapToEntity(loanCollectorDTO, loanCollector);
        return loanCollectorRepository.save(loanCollector).getId();
    }

    public void update(final Long id, final LoanCollectorDTO loanCollectorDTO) {
        final LoanCollector loanCollector = loanCollectorRepository.findById(id)
                .orElseThrow(NotFoundException::new);
        mapToEntity(loanCollectorDTO, loanCollector);
        loanCollectorRepository.save(loanCollector);
    }

    public void delete(final Long id) {
        loanCollectorRepository.deleteById(id);
    }

    private LoanCollectorDTO mapToDTO(final LoanCollector loanCollector,
            final LoanCollectorDTO loanCollectorDTO) {
        loanCollectorDTO.setId(loanCollector.getId());
        loanCollectorDTO.setDateCreated(loanCollector.getDateCreated());
        loanCollectorDTO.setIdPerson(loanCollector.getIdPerson());
        loanCollectorDTO.setLastUpdated(loanCollector.getLastUpdated());
        loanCollectorDTO.setPerson(loanCollector.getPerson() == null ? null : loanCollector.getPerson().getId());
        loanCollectorDTO.setLcStatus(loanCollector.getLcStatus() == null ? null : loanCollector.getLcStatus().getId());
        return loanCollectorDTO;
    }

    private LoanCollector mapToEntity(final LoanCollectorDTO loanCollectorDTO,
            final LoanCollector loanCollector) {
        loanCollector.setDateCreated(loanCollectorDTO.getDateCreated());
        loanCollector.setIdPerson(loanCollectorDTO.getIdPerson());
        loanCollector.setLastUpdated(loanCollectorDTO.getLastUpdated());
        final Person person = loanCollectorDTO.getPerson() == null ? null : personRepository.findById(loanCollectorDTO.getPerson())
                .orElseThrow(() -> new NotFoundException("person not found"));
        loanCollector.setPerson(person);
        final LoanCollectorStatus lcStatus = loanCollectorDTO.getLcStatus() == null ? null : loanCollectorStatusRepository.findById(loanCollectorDTO.getLcStatus())
                .orElseThrow(() -> new NotFoundException("lcStatus not found"));
        loanCollector.setLcStatus(lcStatus);
        return loanCollector;
    }

    public boolean idPersonExists(final Long idPerson) {
        return loanCollectorRepository.existsByIdPerson(idPerson);
    }

}

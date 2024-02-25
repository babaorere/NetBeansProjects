package com.isiweek.criminal_record;

import com.isiweek.person.Person;
import com.isiweek.person.PersonRepository;
import com.isiweek.util.NotFoundException;
import com.isiweek.util.ReferencedWarning;
import java.util.List;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;


@Service
public class CriminalRecordService {

    private final CriminalRecordRepository criminalRecordRepository;
    private final PersonRepository personRepository;

    public CriminalRecordService(final CriminalRecordRepository criminalRecordRepository,
            final PersonRepository personRepository) {
        this.criminalRecordRepository = criminalRecordRepository;
        this.personRepository = personRepository;
    }

    public List<CriminalRecordDTO> findAll() {
        final List<CriminalRecord> criminalRecords = criminalRecordRepository.findAll(Sort.by("id"));
        return criminalRecords.stream()
                .map(criminalRecord -> mapToDTO(criminalRecord, new CriminalRecordDTO()))
                .toList();
    }

    public CriminalRecordDTO get(final Long id) {
        return criminalRecordRepository.findById(id)
                .map(criminalRecord -> mapToDTO(criminalRecord, new CriminalRecordDTO()))
                .orElseThrow(NotFoundException::new);
    }

    public Long create(final CriminalRecordDTO criminalRecordDTO) {
        final CriminalRecord criminalRecord = new CriminalRecord();
        mapToEntity(criminalRecordDTO, criminalRecord);
        return criminalRecordRepository.save(criminalRecord).getId();
    }

    public void update(final Long id, final CriminalRecordDTO criminalRecordDTO) {
        final CriminalRecord criminalRecord = criminalRecordRepository.findById(id)
                .orElseThrow(NotFoundException::new);
        mapToEntity(criminalRecordDTO, criminalRecord);
        criminalRecordRepository.save(criminalRecord);
    }

    public void delete(final Long id) {
        criminalRecordRepository.deleteById(id);
    }

    private CriminalRecordDTO mapToDTO(final CriminalRecord criminalRecord,
            final CriminalRecordDTO criminalRecordDTO) {
        criminalRecordDTO.setId(criminalRecord.getId());
        criminalRecordDTO.setDateCreated(criminalRecord.getDateCreated());
        criminalRecordDTO.setLastUpdated(criminalRecord.getLastUpdated());
        criminalRecordDTO.setName(criminalRecord.getName());
        criminalRecordDTO.setDescription(criminalRecord.getDescription());
        return criminalRecordDTO;
    }

    private CriminalRecord mapToEntity(final CriminalRecordDTO criminalRecordDTO,
            final CriminalRecord criminalRecord) {
        criminalRecord.setDateCreated(criminalRecordDTO.getDateCreated());
        criminalRecord.setLastUpdated(criminalRecordDTO.getLastUpdated());
        criminalRecord.setName(criminalRecordDTO.getName());
        criminalRecord.setDescription(criminalRecordDTO.getDescription());
        return criminalRecord;
    }

    public boolean nameExists(final String name) {
        return criminalRecordRepository.existsByNameIgnoreCase(name);
    }

    public ReferencedWarning getReferencedWarning(final Long id) {
        final ReferencedWarning referencedWarning = new ReferencedWarning();
        final CriminalRecord criminalRecord = criminalRecordRepository.findById(id)
                .orElseThrow(NotFoundException::new);
        final Person criminalRecordPerson = personRepository.findFirstByCriminalRecord(criminalRecord);
        if (criminalRecordPerson != null) {
            referencedWarning.setKey("criminalRecord.person.criminalRecord.referenced");
            referencedWarning.addParam(criminalRecordPerson.getId());
            return referencedWarning;
        }
        return null;
    }

}

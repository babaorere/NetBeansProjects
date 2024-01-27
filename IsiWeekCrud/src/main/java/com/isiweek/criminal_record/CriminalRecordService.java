package com.isiweek.criminal_record;

import com.isiweek.person.Person;
import com.isiweek.person.PersonRepository;
import com.isiweek.util.NotFoundException;
import com.isiweek.util.WebUtils;
import java.util.List;
import java.util.Optional;
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

    public List<CriminalRecord> findAll() {
        return criminalRecordRepository.findAll(Sort.by("id"));
    }

    public Optional<CriminalRecord> get(final Long id) {
        return criminalRecordRepository.findById(id);
    }

    public Long create(final CriminalRecord inCriminalRecord) {
        return criminalRecordRepository.save(inCriminalRecord).getId();
    }

    public void update(final Long id, final CriminalRecord criminalRecord) {
        criminalRecordRepository.save(criminalRecord);
    }

    public void delete(final Long id) {
        criminalRecordRepository.deleteById(id);
    }

    public boolean nameExists(final String name) {
        return criminalRecordRepository.existsByNameIgnoreCase(name);
    }

    public String getReferencedWarning(final Long id) {
        final CriminalRecord criminalRecord = criminalRecordRepository.findById(id)
                .orElseThrow(NotFoundException::new);
        final Person criminalRecordPerson = personRepository.findFirstByCriminalRecord(criminalRecord);
        if (criminalRecordPerson != null) {
            return WebUtils.getMessage("criminalRecord.person.criminalRecord.referenced", criminalRecordPerson.getId());
        }

        return null;
    }

}

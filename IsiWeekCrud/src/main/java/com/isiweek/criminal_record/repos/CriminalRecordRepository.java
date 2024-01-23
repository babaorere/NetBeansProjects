package com.isiweek.criminal_record.repos;

import com.isiweek.criminal_record.domain.CriminalRecord;
import org.springframework.data.jpa.repository.JpaRepository;


public interface CriminalRecordRepository extends JpaRepository<CriminalRecord, Long> {

    boolean existsByNameIgnoreCase(String name);

}

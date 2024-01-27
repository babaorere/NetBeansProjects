package com.isiweek.criminal_record;

import com.isiweek.criminal_record.CriminalRecord;
import org.springframework.data.jpa.repository.JpaRepository;


public interface CriminalRecordRepository extends JpaRepository<CriminalRecord, Long> {

    boolean existsByNameIgnoreCase(String name);

}

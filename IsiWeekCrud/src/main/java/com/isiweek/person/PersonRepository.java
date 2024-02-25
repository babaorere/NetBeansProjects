package com.isiweek.person;

import com.isiweek.country.Country;
import com.isiweek.criminal_record.CriminalRecord;
import com.isiweek.doc_type.DocType;
import com.isiweek.marital_status.MaritalStatus;
import com.isiweek.user.User;
import org.springframework.data.jpa.repository.JpaRepository;


public interface PersonRepository extends JpaRepository<Person, Long> {

    Person findFirstByDocType(DocType docType);

    Person findFirstByCriminalRecord(CriminalRecord criminalRecord);

    Person findFirstByCountry(Country country);

    Person findFirstByMaritalStatus(MaritalStatus maritalStatus);

    Person findFirstByUser(User user);

    boolean existsByIdDocIgnoreCase(String idDoc);

    boolean existsByEmailIgnoreCase(String email);

}

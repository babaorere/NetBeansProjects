package com.isiweek.person.repos;

import com.isiweek.company.Company;
import com.isiweek.country.domain.Country;
import com.isiweek.criminal_record.domain.CriminalRecord;
import com.isiweek.doc_type.domain.DocType;
import com.isiweek.marital_status.domain.MaritalStatus;
import com.isiweek.person.domain.Person;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PersonRepository extends JpaRepository<Person, Long> {

    Person findFirstByMaritalStatus(MaritalStatus maritalStatus);

    Person findFirstByDocType(DocType docType);

    Person findFirstByCountry(Country country);

    Person findFirstByCompanies(Company company);

    Person findFirstByCriminalRecord(CriminalRecord criminalRecord);

    boolean existsByIdDocIgnoreCase(String inIdDoc);

    boolean existsByEmailIgnoreCase(String email);

}

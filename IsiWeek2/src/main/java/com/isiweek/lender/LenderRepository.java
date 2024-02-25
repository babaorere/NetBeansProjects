package com.isiweek.lender;

import com.isiweek.person.Person;
import org.springframework.data.jpa.repository.JpaRepository;


public interface LenderRepository extends JpaRepository<Lender, Long> {

    Lender findFirstByPerson(Person person);

    boolean existsByPersonId(Long id);

}

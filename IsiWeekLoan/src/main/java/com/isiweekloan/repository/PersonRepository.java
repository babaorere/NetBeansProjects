package com.isiweekloan.repository;

import com.isiweekloan.entity.Person;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 *
 * @author
 */
@Repository
public interface PersonRepository extends JpaRepository<Person, Long> {

}

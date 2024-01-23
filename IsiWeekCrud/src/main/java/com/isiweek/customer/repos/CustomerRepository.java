package com.isiweek.customer.repos;

import com.isiweek.customer.domain.Customer;
import com.isiweek.person.domain.Person;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CustomerRepository extends JpaRepository<Customer, Long> {

    Customer findFirstByPerson(Person person);

}

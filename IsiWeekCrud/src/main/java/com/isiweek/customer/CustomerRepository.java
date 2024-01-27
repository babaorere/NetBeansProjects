package com.isiweek.customer;

import com.isiweek.customer.Customer;
import com.isiweek.person.Person;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CustomerRepository extends JpaRepository<Customer, Long> {

    Customer findFirstByPerson(Person person);

}

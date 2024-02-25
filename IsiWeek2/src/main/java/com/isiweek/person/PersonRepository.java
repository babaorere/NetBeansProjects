package com.isiweek.person;

import com.isiweek.user.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PersonRepository extends JpaRepository<Person, Long> {

    Person findFirstByUser(User user);

    boolean existsByIdDocIgnoreCase(String idDoc);

    boolean existsByEmailIgnoreCase(String email);

}

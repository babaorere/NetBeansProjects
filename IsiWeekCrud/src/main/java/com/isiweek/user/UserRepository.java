package com.isiweek.user;

import com.isiweek.lender.Lender;
import com.isiweek.role.Role;
import org.springframework.data.jpa.repository.JpaRepository;


public interface UserRepository extends JpaRepository<User, Long> {

    User findFirstByRole(Role role);

    User findFirstByLender(Lender lender);

    boolean existsByNameIgnoreCase(String name);

}

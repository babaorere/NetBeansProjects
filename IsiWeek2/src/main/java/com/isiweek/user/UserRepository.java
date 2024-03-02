package com.isiweek.user;

import com.isiweek.debtor.Debtor;
import com.isiweek.lender.Lender;
import com.isiweek.role.Role;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {

    Optional<User> findFirstByRole(Role role);

    Optional<User> findFirstByLender(Lender lender);

    Optional<User> findFirstByDebtor(Debtor debtor);

    Boolean existsByUsernameIgnoreCase(String name);

    Optional<User> findByUsernameIgnoreCase(String username);

}

package com.isiweek.role;

import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RoleRepository extends JpaRepository<Role, Long> {

    Boolean existsByName(String roleName);

    public Optional<Role> findByName(String roleName);

}

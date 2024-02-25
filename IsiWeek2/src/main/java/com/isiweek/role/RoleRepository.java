package com.isiweek.role;

import org.springframework.data.jpa.repository.JpaRepository;


public interface RoleRepository extends JpaRepository<Role, Long> {

    boolean existsByRoleEnumIgnoreCase(String roleEnum);

}

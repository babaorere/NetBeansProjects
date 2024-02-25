package com.isiweek.departament;

import org.springframework.data.jpa.repository.JpaRepository;


public interface DepartamentRepository extends JpaRepository<Departament, Long> {

    boolean existsByNameIgnoreCase(String name);

}

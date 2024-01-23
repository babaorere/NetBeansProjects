package com.isiweek.departament.repos;

import com.isiweek.departament.domain.Departament;
import org.springframework.data.jpa.repository.JpaRepository;


public interface DepartamentRepository extends JpaRepository<Departament, Long> {

    boolean existsByNameIgnoreCase(String name);

}

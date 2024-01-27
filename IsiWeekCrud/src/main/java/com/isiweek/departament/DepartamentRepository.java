package com.isiweek.departament;

import com.isiweek.departament.Departament;
import org.springframework.data.jpa.repository.JpaRepository;


public interface DepartamentRepository extends JpaRepository<Departament, Long> {

    boolean existsByNameIgnoreCase(String name);

}

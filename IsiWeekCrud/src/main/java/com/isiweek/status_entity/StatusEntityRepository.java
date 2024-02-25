package com.isiweek.status_entity;

import org.springframework.data.jpa.repository.JpaRepository;


public interface StatusEntityRepository extends JpaRepository<StatusEntity, Long> {

    boolean existsByName(StatusEnum name);

}

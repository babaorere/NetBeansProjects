package com.isiweek.status;

import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface StatusRepository extends JpaRepository<Status, Long> {

    Boolean existsByName(String name);

    Optional<Status> findByName(String name);

}

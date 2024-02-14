package com.isiweek.status;

import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface StatusRepository extends JpaRepository<Status, Long> {

    boolean existsByStatusEnum(StatusEnum inStatusEnum);

    @Query("SELECT MIN(c.id) FROM Status c")
    Optional<Long> findFirstId();

    @Query("SELECT s FROM Status s WHERE s.id = (SELECT MIN(s.id) FROM Status s)")
    Optional<Status> findFirst();

    @Query("SELECT MAX(c.id) FROM Status c")
    Optional<Long> findLastId();

    @Query("SELECT s FROM Status s WHERE s.id = (SELECT MAX(s.id) FROM Status s)")
    Optional<Status> findLast();
}

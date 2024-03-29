package com.isiweek.status;

import java.util.Optional;
import java.util.Set;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface StatusRepository extends JpaRepository<Status, Long> {

    boolean existsByStatusEnum(StatusEnum inStatusEnum);

    @Query("SELECT MIN(c.id) FROM Status c")
    Optional<Long> findFirstId();

    @Query("SELECT s FROM Status s WHERE s.id = (SELECT MIN(s.id) FROM Status s)")
    Optional<Status> findFirst();

    Optional<Status> findByStatusEnum(StatusEnum inStatusEnum);

    @Query("SELECT MAX(c.id) FROM Status c")
    Optional<Long> findLastId();

    @Query("SELECT s FROM Status s WHERE s.id = (SELECT MAX(s.id) FROM Status s)")
    Optional<Status> findLast();

    // Elimina los registros que no esten incluidos iniciales dentro de StatusEnum
    @Modifying
    @Query("DELETE FROM Status s WHERE s.statusEnum NOT IN :statusEnums")
    void deleteNotStatusEnum(@Param("statusEnums") Set<StatusEnum> statusEnums);
}

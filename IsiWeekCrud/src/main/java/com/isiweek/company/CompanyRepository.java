package com.isiweek.company;

import com.isiweek.status.Status;
import com.isiweek.status.StatusEnum;
import java.util.Optional;
import java.util.Set;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface CompanyRepository extends JpaRepository<Company, Long> {

    @Query("SELECT MIN(c.id) FROM Company c")
    Optional<Long> findFirstId();

    @Query("SELECT c FROM Company c WHERE c.id = (SELECT MIN(c.id) FROM Company c)")
    Optional<Company> findFirst();

    @Query("SELECT MAX(c.id) FROM Company c")
    Optional<Long> findLastId();

    @Query("SELECT c FROM Company c WHERE c.id = (SELECT MIN(c.id) FROM Company c)")
    Optional<Company> findLast();

    Optional<Company> findFirstByStatusOrderByIdAsc(Status status);

    boolean existsByEmailIgnoreCase(String email);

    boolean existsByTaxidnumberIgnoreCase(String taxidnumber);

    boolean existsByNameIgnoreCase(String name);

    public Optional<Company> findByName(String name);

    public Optional<Company> findById(Long id);

    @Modifying
    @Query("DELETE FROM Company c WHERE c.status.statusEnum NOT IN :statusEnums")
    void deleteCompaniesByStatusNotIn(@Param("statusEnums") Set<StatusEnum> statusEnums);

}

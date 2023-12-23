package com.isiweekloan.repository;

import com.isiweekloan.entity.CompanyEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

@Repository
public interface CompanyRepository extends JpaRepository<CompanyEntity, Collection<PersonEntity>>, JpaSpecificationExecutor<CompanyEntity> {
}
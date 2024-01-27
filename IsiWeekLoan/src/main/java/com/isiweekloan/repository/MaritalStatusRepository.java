package com.isiweekloan.repository;

import com.isiweekloan.entity.MaritalStatusEntity;
import com.isiweekloan.entity.PersonEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.Collection;

@Repository
public interface MaritalStatusRepository extends JpaRepository<MaritalStatusEntity, Long>, JpaSpecificationExecutor<MaritalStatusEntity> {
}
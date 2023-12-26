package com.isiweekloan.repository;

import com.isiweekloan.entity.DepartamentEntity;
import com.isiweekloan.entity.EmployeeEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.Collection;

@Repository
public interface DepartamentRepository extends JpaRepository<DepartamentEntity, Long>, JpaSpecificationExecutor<DepartamentEntity> {
}
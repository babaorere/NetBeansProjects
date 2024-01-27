package com.isiweekloan.repository;

import com.isiweekloan.entity.EmployeeEntity;
import com.isiweekloan.entity.JobTitleEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.Collection;

@Repository
public interface JobTitleRepository extends JpaRepository<JobTitleEntity, Long>, JpaSpecificationExecutor<JobTitleEntity> {
}
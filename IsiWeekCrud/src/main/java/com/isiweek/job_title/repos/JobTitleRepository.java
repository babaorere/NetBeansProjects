package com.isiweek.job_title.repos;

import com.isiweek.job_title.domain.JobTitle;
import org.springframework.data.jpa.repository.JpaRepository;


public interface JobTitleRepository extends JpaRepository<JobTitle, Long> {

    boolean existsByNameIgnoreCase(String name);

}

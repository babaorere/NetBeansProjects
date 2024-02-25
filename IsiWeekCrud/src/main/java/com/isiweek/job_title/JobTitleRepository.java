package com.isiweek.job_title;

import org.springframework.data.jpa.repository.JpaRepository;


public interface JobTitleRepository extends JpaRepository<JobTitle, Long> {

    boolean existsByNameIgnoreCase(String name);

}

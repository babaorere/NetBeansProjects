package com.isiweek.job_title.service;

import com.isiweek.employee.domain.Employee;
import com.isiweek.employee.repos.EmployeeRepository;
import com.isiweek.job_title.domain.JobTitle;
import com.isiweek.job_title.model.JobTitleDTO;
import com.isiweek.job_title.repos.JobTitleRepository;
import com.isiweek.util.NotFoundException;
import com.isiweek.util.WebUtils;
import java.util.List;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

@Service
public class JobTitleService {

    private final JobTitleRepository jobTitleRepository;
    private final EmployeeRepository employeeRepository;

    public JobTitleService(final JobTitleRepository jobTitleRepository,
            final EmployeeRepository employeeRepository) {
        this.jobTitleRepository = jobTitleRepository;
        this.employeeRepository = employeeRepository;
    }

    public List<JobTitleDTO> findAll() {
        final List<JobTitle> jobTitles = jobTitleRepository.findAll(Sort.by("id"));
        return jobTitles.stream()
                .map(jobTitle -> mapToDTO(jobTitle, new JobTitleDTO()))
                .toList();
    }

    public JobTitleDTO get(final Long id) {
        return jobTitleRepository.findById(id)
                .map(jobTitle -> mapToDTO(jobTitle, new JobTitleDTO()))
                .orElseThrow(NotFoundException::new);
    }

    public Long create(final JobTitleDTO jobTitleDTO) {
        final JobTitle jobTitle = new JobTitle();
        mapToEntity(jobTitleDTO, jobTitle);
        return jobTitleRepository.save(jobTitle).getId();
    }

    public void update(final Long id, final JobTitleDTO jobTitleDTO) {
        final JobTitle jobTitle = jobTitleRepository.findById(id)
                .orElseThrow(NotFoundException::new);
        mapToEntity(jobTitleDTO, jobTitle);
        jobTitleRepository.save(jobTitle);
    }

    public void delete(final Long id) {
        jobTitleRepository.deleteById(id);
    }

    private JobTitleDTO mapToDTO(final JobTitle jobTitle, final JobTitleDTO jobTitleDTO) {
        jobTitleDTO.setId(jobTitle.getId());
        jobTitleDTO.setName(jobTitle.getName());
        jobTitleDTO.setDescription(jobTitle.getDescription());
        return jobTitleDTO;
    }

    private JobTitle mapToEntity(final JobTitleDTO jobTitleDTO, final JobTitle jobTitle) {
        jobTitle.setName(jobTitleDTO.getName());
        jobTitle.setDescription(jobTitleDTO.getDescription());
        return jobTitle;
    }

    public boolean nameExists(final String name) {
        return jobTitleRepository.existsByNameIgnoreCase(name);
    }

    public String getReferencedWarning(final Long id) {
        final JobTitle jobTitle = jobTitleRepository.findById(id)
                .orElseThrow(NotFoundException::new);
        final Employee jobTitleEmployee = employeeRepository.findFirstByJobTitle(jobTitle);
        if (jobTitleEmployee != null) {
            return WebUtils.getMessage("jobTitle.employee.jobTitle.referenced", jobTitleEmployee.getId());
        }

        return null;
    }

}

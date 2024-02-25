package com.isiweek.job_title;

import com.isiweek.employee.Employee;
import com.isiweek.employee.EmployeeRepository;
import com.isiweek.util.NotFoundException;
import com.isiweek.util.ReferencedWarning;
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
        jobTitleDTO.setDateCreated(jobTitle.getDateCreated());
        jobTitleDTO.setLastUpdated(jobTitle.getLastUpdated());
        jobTitleDTO.setName(jobTitle.getName());
        jobTitleDTO.setDescription(jobTitle.getDescription());
        return jobTitleDTO;
    }

    private JobTitle mapToEntity(final JobTitleDTO jobTitleDTO, final JobTitle jobTitle) {
        jobTitle.setDateCreated(jobTitleDTO.getDateCreated());
        jobTitle.setLastUpdated(jobTitleDTO.getLastUpdated());
        jobTitle.setName(jobTitleDTO.getName());
        jobTitle.setDescription(jobTitleDTO.getDescription());
        return jobTitle;
    }

    public boolean nameExists(final String name) {
        return jobTitleRepository.existsByNameIgnoreCase(name);
    }

    public ReferencedWarning getReferencedWarning(final Long id) {
        final ReferencedWarning referencedWarning = new ReferencedWarning();
        final JobTitle jobTitle = jobTitleRepository.findById(id)
                .orElseThrow(NotFoundException::new);
        final Employee jobTitleEmployee = employeeRepository.findFirstByJobTitle(jobTitle);
        if (jobTitleEmployee != null) {
            referencedWarning.setKey("jobTitle.employee.jobTitle.referenced");
            referencedWarning.addParam(jobTitleEmployee.getId());
            return referencedWarning;
        }
        return null;
    }

}

package com.isiweekloan.service;

import com.isiweekloan.entity.JobTitleEntity;
import com.isiweekloan.repository.JobTitleRepository;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class JobTitleService {

    private final JobTitleRepository jobTitleRepository;

    @Autowired
    public JobTitleService(JobTitleRepository jobTitleRepository) {
        this.jobTitleRepository = jobTitleRepository;
    }

    @Transactional(readOnly = true)
    public List<JobTitleEntity> getAllJobTitles() {
        return jobTitleRepository.findAll();
    }

    @Transactional(readOnly = true)
    public Optional<JobTitleEntity> getJobTitleById(Long id) {
        return jobTitleRepository.findById(id);
    }

    @Transactional
    public JobTitleEntity createJobTitle(JobTitleEntity jobTitleEntity) {
        validateJobTitle(jobTitleEntity);
        return jobTitleRepository.save(jobTitleEntity);
    }

    @Transactional
    public Optional<JobTitleEntity> updateJobTitle(Long id, JobTitleEntity jobTitleEntity) {
        Optional<JobTitleEntity> optionalExistingJobTitle = jobTitleRepository.findById(id);

        return optionalExistingJobTitle.map(existingJobTitle -> {
            validateJobTitle(jobTitleEntity);
            // Update fields if needed
            existingJobTitle.setName(jobTitleEntity.getName());
            existingJobTitle.setDescription(jobTitleEntity.getDescription());
            return Optional.of(jobTitleRepository.save(existingJobTitle));
        }).orElse(Optional.empty());
    }

    @Transactional
    public boolean deleteJobTitle(Long id) {
        if (jobTitleRepository.existsById(id)) {
            jobTitleRepository.deleteById(id);
            return true;
        } else {
            return false;
        }
    }

    private void validateJobTitle(JobTitleEntity jobTitleEntity) {
        Objects.requireNonNull(jobTitleEntity.getName(), "Name cannot be null");
        Objects.requireNonNull(jobTitleEntity.getDescription(), "Description cannot be null");

        // Additional validation if needed
    }
}

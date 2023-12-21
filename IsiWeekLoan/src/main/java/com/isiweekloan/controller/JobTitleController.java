package com.isiweekloan.controller;

import com.isiweekloan.entity.JobTitleEntity;
import com.isiweekloan.repository.JobTitleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/job-titles")
public class JobTitleController {

    private final JobTitleRepository jobTitleRepository;

    @Autowired
    public JobTitleController(JobTitleRepository jobTitleRepository) {
        this.jobTitleRepository = jobTitleRepository;
    }

    @GetMapping
    public List<JobTitleEntity> getAllJobTitles() {
        return jobTitleRepository.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<JobTitleEntity> getJobTitleById(@PathVariable Long id) {
        Optional<JobTitleEntity> optionalJobTitle = jobTitleRepository.findById(id);
        return optionalJobTitle.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<JobTitleEntity> createJobTitle(@RequestBody JobTitleEntity jobTitleEntity) {
        validateJobTitle(jobTitleEntity);
        JobTitleEntity createdJobTitle = jobTitleRepository.save(jobTitleEntity);
        return ResponseEntity.status(201).body(createdJobTitle);
    }

    @PutMapping("/{id}")
    public ResponseEntity<JobTitleEntity> updateJobTitle(@PathVariable Long id, @RequestBody JobTitleEntity jobTitleEntity) {
        Optional<JobTitleEntity> optionalExistingJobTitle = jobTitleRepository.findById(id);

        return optionalExistingJobTitle.map(existingJobTitle -> {
            validateJobTitle(jobTitleEntity);
            // Update fields if needed
            existingJobTitle.setName(jobTitleEntity.getName());
            existingJobTitle.setDescription(jobTitleEntity.getDescription());
            return ResponseEntity.ok(jobTitleRepository.save(existingJobTitle));
        }).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteJobTitle(@PathVariable Long id) {
        if (jobTitleRepository.existsById(id)) {
            jobTitleRepository.deleteById(id);
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    private void validateJobTitle(JobTitleEntity jobTitleEntity) {
        if (jobTitleEntity.getName() == null || jobTitleEntity.getDescription() == null) {
            throw new IllegalArgumentException("Required attributes cannot be null");
        }

        // Additional validation if needed
    }
}

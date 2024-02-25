package com.isiweek.job_title;

import com.isiweek.util.ReferencedException;
import com.isiweek.util.ReferencedWarning;
import jakarta.validation.Valid;
import java.util.List;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping(value = "/api/jobTitles", produces = MediaType.APPLICATION_JSON_VALUE)
public class JobTitleResource {

    private final JobTitleService jobTitleService;

    public JobTitleResource(final JobTitleService jobTitleService) {
        this.jobTitleService = jobTitleService;
    }

    @GetMapping
    public ResponseEntity<List<JobTitleDTO>> getAllJobTitles() {
        return ResponseEntity.ok(jobTitleService.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<JobTitleDTO> getJobTitle(@PathVariable(name = "id") final Long id) {
        return ResponseEntity.ok(jobTitleService.get(id));
    }

    @PostMapping
    public ResponseEntity<Long> createJobTitle(@RequestBody @Valid final JobTitleDTO jobTitleDTO) {
        final Long createdId = jobTitleService.create(jobTitleDTO);
        return new ResponseEntity<>(createdId, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Long> updateJobTitle(@PathVariable(name = "id") final Long id,
            @RequestBody @Valid final JobTitleDTO jobTitleDTO) {
        jobTitleService.update(id, jobTitleDTO);
        return ResponseEntity.ok(id);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteJobTitle(@PathVariable(name = "id") final Long id) {
        final ReferencedWarning referencedWarning = jobTitleService.getReferencedWarning(id);
        if (referencedWarning != null) {
            throw new ReferencedException(referencedWarning);
        }
        jobTitleService.delete(id);
        return ResponseEntity.noContent().build();
    }

}

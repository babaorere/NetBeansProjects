package com.isiweekloan.controller;

import com.isiweekloan.dto.JobTitleDto;
import com.isiweekloan.entity.JobTitleEntity;
import com.isiweekloan.exception.ResourceNotFoundException;
import com.isiweekloan.mapper.JobTitleMapper;
import com.isiweekloan.service.JobTitleService;
import io.swagger.annotations.Api;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RequestMapping("/job-title")
@RestController
@Slf4j
@Api("job-title")
public class JobTitleController {
    private final JobTitleService jobTitleService;

    public JobTitleController(JobTitleService jobTitleService) {
        this.jobTitleService = jobTitleService;
    }

    @PostMapping
    public ResponseEntity<Void> save(@RequestBody @Validated JobTitleDto jobTitleDto) {
        jobTitleService.save(jobTitleDto);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/{id}")
    public ResponseEntity<JobTitleDto> findById(@PathVariable("id") Long id) {
        JobTitleDto jobTitle = jobTitleService.findById(id);
        return ResponseEntity.ok(jobTitle);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable("id") Long id) {
        try {
            JobTitleDto existingDto = jobTitleService.findById(id);

            if (existingDto == null) {
                log.error("Unable to delete non-existent data with ID {}", id);
                throw new ResourceNotFoundException("Unable to delete non-existent data with ID " + id);
            }

            jobTitleService.deleteById(id);
            log.info("Data with ID {} deleted successfully", id);

            return ResponseEntity.noContent().build();
        } catch (Exception e) {
            log.error("Error deleting data with ID {}: {}", id, e.getMessage());
            // Puedes lanzar una excepción diferente o manejarla de otra manera según tus requisitos.
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @GetMapping("/page-query")
    public ResponseEntity<Page<JobTitleDto>> pageQuery(JobTitleDto jobTitleDto, @PageableDefault(sort = "createAt", direction = Sort.Direction.DESC) Pageable pageable) {
        Page<JobTitleDto> jobTitlePage = jobTitleService.findByCondition(jobTitleDto, pageable);
        return ResponseEntity.ok(jobTitlePage);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Void> update(@RequestBody @Validated JobTitleDto jobTitleDto, @PathVariable("id") Long id) {
        jobTitleService.update(jobTitleDto, id);
        return ResponseEntity.ok().build();
    }
}

package com.isiweekloan.service;

import com.isiweekloan.dto.JobTitleDto;
import com.isiweekloan.entity.EmployeeEntity;
import com.isiweekloan.entity.JobTitleEntity;
import com.isiweekloan.exception.ResourceNotFoundException;
import com.isiweekloan.mapper.JobTitleMapper;
import com.isiweekloan.repository.JobTitleRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

@Slf4j
@Service
@Transactional
public class JobTitleService {
    private final JobTitleRepository repository;
    private final JobTitleMapper jobTitleMapper;

    public JobTitleService(JobTitleRepository repository, JobTitleMapper jobTitleMapper) {
        this.repository = repository;
        this.jobTitleMapper = jobTitleMapper;
    }

    public JobTitleDto save(JobTitleDto jobTitleDto) {
        JobTitleEntity entity = jobTitleMapper.toEntity(jobTitleDto);
        return jobTitleMapper.toDto(repository.save(entity));
    }

    public void deleteById(Long id) {
        repository.deleteById(id);
    }

    public JobTitleDto findById(Long id) {
        try {
            return jobTitleMapper.toDto(repository.findById(id)
                    .orElseThrow(() -> new ResourceNotFoundException("Could not find")));
        } catch (Exception e) {
            return null;
        }
    }

    public Page<JobTitleDto> findByCondition(JobTitleDto jobTitleDto, Pageable pageable) {
        Page<JobTitleEntity> entityPage = repository.findAll(pageable);
        List<JobTitleEntity> entities = entityPage.getContent();
        return new PageImpl<>(jobTitleMapper.toDto(entities), pageable, entityPage.getTotalElements());
    }

    public JobTitleDto update(JobTitleDto jobTitleDto, Long id) {
        JobTitleDto data = findById(id);
        JobTitleEntity entity = jobTitleMapper.toEntity(jobTitleDto);
        BeanUtils.copyProperties(data, entity);
        return save(jobTitleMapper.toDto(entity));
    }
}

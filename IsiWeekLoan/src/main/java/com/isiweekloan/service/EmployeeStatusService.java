package com.isiweekloan.service;

import com.isiweekloan.dto.EmployeeStatusDto;
import com.isiweekloan.entity.EmployeeEntity;
import com.isiweekloan.entity.EmployeeStatusEntity;
import com.isiweekloan.exception.ResourceNotFoundException;
import com.isiweekloan.mapper.EmployeeStatusMapper;
import com.isiweekloan.repository.EmployeeStatusRepository;
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
public class EmployeeStatusService {
    private final EmployeeStatusRepository repository;
    private final EmployeeStatusMapper employeeStatusMapper;

    public EmployeeStatusService(EmployeeStatusRepository repository, EmployeeStatusMapper employeeStatusMapper) {
        this.repository = repository;
        this.employeeStatusMapper = employeeStatusMapper;
    }

    public EmployeeStatusDto save(EmployeeStatusDto employeeStatusDto) {
        EmployeeStatusEntity entity = employeeStatusMapper.toEntity(employeeStatusDto);
        return employeeStatusMapper.toDto(repository.save(entity));
    }

    public void deleteById(Long id) {
        repository.deleteById(id);
    }

    public EmployeeStatusDto findById(Long id) {
        try {
            return employeeStatusMapper.toDto(repository.findById(id)
                    .orElseThrow(() -> new ResourceNotFoundException("Could not find")));
        } catch (Exception e) {
            return null;
        }
    }

    public Page<EmployeeStatusDto> findByCondition(EmployeeStatusDto employeeStatusDto, Pageable pageable) {
        Page<EmployeeStatusEntity> entityPage = repository.findAll(pageable);
        List<EmployeeStatusEntity> entities = entityPage.getContent();
        return new PageImpl<>(employeeStatusMapper.toDto(entities), pageable, entityPage.getTotalElements());
    }

    public EmployeeStatusDto update(EmployeeStatusDto employeeStatusDto, Long id) {
        EmployeeStatusDto data = findById(id);
        EmployeeStatusEntity entity = employeeStatusMapper.toEntity(employeeStatusDto);
        BeanUtils.copyProperties(data, entity);
        return save(employeeStatusMapper.toDto(entity));
    }
}

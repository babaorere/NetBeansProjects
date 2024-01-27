package com.isiweekloan.service;

import com.isiweekloan.dto.EmployeeDto;
import com.isiweekloan.entity.EmployeeEntity;
import com.isiweekloan.exception.ResourceNotFoundException;
import com.isiweekloan.mapper.EmployeeMapper;
import com.isiweekloan.repository.EmployeeRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Date;
import java.util.List;
import java.util.Optional;

@Slf4j
@Service
@Transactional
public class EmployeeService {
    private final EmployeeRepository repository;
    private final EmployeeMapper employeeMapper;

    public EmployeeService(EmployeeRepository repository, EmployeeMapper employeeMapper) {
        this.repository = repository;
        this.employeeMapper = employeeMapper;
    }

    public EmployeeDto save(EmployeeDto employeeDto) {
        EmployeeEntity entity = employeeMapper.toEntity(employeeDto);
        return employeeMapper.toDto(repository.save(entity));
    }

    public void deleteById(Long id) {
        repository.deleteById(id);
    }

    public EmployeeDto findById(Long id) {
        try {
            return employeeMapper.toDto(repository.findById(id)
                    .orElseThrow(() -> new ResourceNotFoundException("Could not find")));
        } catch (Exception e) {
            return null;
        }
    }

    public Page<EmployeeDto> findByCondition(EmployeeDto employeeDto, Pageable pageable) {
        Page<EmployeeEntity> entityPage = repository.findAll(pageable);
        List<EmployeeEntity> entities = entityPage.getContent();
        return new PageImpl<>(employeeMapper.toDto(entities), pageable, entityPage.getTotalElements());
    }

    public EmployeeDto update(EmployeeDto employeeDto, Long id) {
        EmployeeDto data = findById(id);
        EmployeeEntity entity = employeeMapper.toEntity(employeeDto);
        BeanUtils.copyProperties(data, entity);
        return save(employeeMapper.toDto(entity));
    }
}

package com.isiweekloan.service;

import com.isiweekloan.dto.DepartamentDto;
import com.isiweekloan.entity.DepartamentEntity;
import com.isiweekloan.entity.EmployeeEntity;
import com.isiweekloan.exception.ResourceNotFoundException;
import com.isiweekloan.mapper.DepartamentMapper;
import com.isiweekloan.repository.DepartamentRepository;
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
public class DepartamentService {
    private final DepartamentRepository repository;
    private final DepartamentMapper departamentMapper;

    public DepartamentService(DepartamentRepository repository, DepartamentMapper departamentMapper) {
        this.repository = repository;
        this.departamentMapper = departamentMapper;
    }

    public DepartamentDto save(DepartamentDto departamentDto) {
        DepartamentEntity entity = departamentMapper.toEntity(departamentDto);
        return departamentMapper.toDto(repository.save(entity));
    }

    public void deleteById(Long id) {
        repository.deleteById(id);
    }

    public DepartamentDto findById(Long id) {
        try {
        return departamentMapper.toDto(repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Could not find")));
    } catch(Exception e) {
        return null;
    }
    }

    public Page<DepartamentDto> findByCondition(DepartamentDto departamentDto, Pageable pageable) {
        Page<DepartamentEntity> entityPage = repository.findAll(pageable);
        List<DepartamentEntity> entities = entityPage.getContent();
        return new PageImpl<>(departamentMapper.toDto(entities), pageable, entityPage.getTotalElements());
    }

    public DepartamentDto update(DepartamentDto departamentDto, Long id) {
        DepartamentDto data = findById(id);
        DepartamentEntity entity = departamentMapper.toEntity(departamentDto);
        BeanUtils.copyProperties(data, entity);
        return save(departamentMapper.toDto(entity));
    }
}

package com.isiweekloan.service;

import com.isiweekloan.dto.CompanyDto;
import com.isiweekloan.entity.CompanyEntity;
import com.isiweekloan.entity.PersonEntity;
import com.isiweekloan.exception.ResourceNotFoundException;
import com.isiweekloan.mapper.CompanyMapper;
import com.isiweekloan.repository.CompanyRepository;
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
public class CompanyService {
    private final CompanyRepository repository;
    private final CompanyMapper companyMapper;

    public CompanyService(CompanyRepository repository, CompanyMapper companyMapper) {
        this.repository = repository;
        this.companyMapper = companyMapper;
    }

    public CompanyDto save(CompanyDto companyDto) {
        CompanyEntity entity = companyMapper.toEntity(companyDto);
        return companyMapper.toDto(repository.save(entity));
    }

    public void deleteById(Long id) {
        repository.deleteById(id);
    }

    public CompanyDto findById(Long id) {
        try {
        return companyMapper.toDto(repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Could not find")));
    } catch (Exception e) {
        return null;
    }
    }

    public Page<CompanyDto> findByCondition(CompanyDto companyDto, Pageable pageable) {
        Page<CompanyEntity> entityPage = repository.findAll(pageable);
        List<CompanyEntity> entities = entityPage.getContent();
        return new PageImpl<>(companyMapper.toDto(entities), pageable, entityPage.getTotalElements());
    }

    public CompanyDto update(CompanyDto companyDto, Long id) {
        CompanyDto data = findById(id);
        CompanyEntity entity = companyMapper.toEntity(companyDto);
        BeanUtils.copyProperties(data, entity);
        return save(companyMapper.toDto(entity));
    }
}

package com.isiweekloan.service;

import com.isiweekloan.dto.CompanyDto;
import com.isiweekloan.entity.CompanyEntity;
import com.isiweekloan.mapper.CompanyMapper;
import com.isiweekloan.repository.CompanyRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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

    public CompanyEntityDto save(CompanyEntityDto companyDto) {
        Company entity = companyMapper.toEntity(companyDto);
        return companyMapper.toDto(repository.save(entity));
    }

    public void deleteById(Collection<PersonEntity> id) {
        repository.deleteById(id);
    }

    public CompanyEntityDto findById(Collection<PersonEntity> id) {
        return companyMapper.toDto(repository.findById(id)
                .orElseThrow(ResourceNotFoundException::new));
    }

    public Page<CompanyEntityDto> findByCondition(CompanyEntityDto companyDto, Pageable pageable) {
        Page<CompanyEntity> entityPage = repository.findAll(pageable);
        List<CompanyEntity> entities = entityPage.getContent();
        return new PageImpl<>(companyMapper.toDto(entities), pageable, entityPage.getTotalElements());
    }

    public CompanyEntityDto update(CompanyEntityDto companyDto, Collection<PersonEntity> id) {
        CompanyEntityDto data = findById(id);
        Company entity = companyMapper.toEntity(companyDto);
        BeanUtil.copyProperties(data, entity);
        return save(companyMapper.toDto(entity));
    }
}

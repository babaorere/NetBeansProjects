package com.isiweekloan.service;

import com.isiweekloan.dto.LoanTypeDto;
import com.isiweekloan.entity.LoanContractEntity;
import com.isiweekloan.entity.LoanTypeEntity;
import com.isiweekloan.exception.ResourceNotFoundException;
import com.isiweekloan.mapper.LoanTypeMapper;
import com.isiweekloan.repository.LoanTypeRepository;
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
public class LoanTypeService {
    private final LoanTypeRepository repository;
    private final LoanTypeMapper loanTypeMapper;

    public LoanTypeService(LoanTypeRepository repository, LoanTypeMapper loanTypeMapper) {
        this.repository = repository;
        this.loanTypeMapper = loanTypeMapper;
    }

    public LoanTypeDto save(LoanTypeDto loanTypeDto) {
        LoanTypeEntity entity = loanTypeMapper.toEntity(loanTypeDto);
        return loanTypeMapper.toDto(repository.save(entity));
    }

    public void deleteById(Long id) {
        repository.deleteById(id);
    }

    public LoanTypeDto findById(Long id) {
        try {
            return loanTypeMapper.toDto(repository.findById(id)
                    .orElseThrow(() -> new ResourceNotFoundException("Could not find")));
        } catch (Exception e) {
            return null;
        }
    }

    public Page<LoanTypeDto> findByCondition(LoanTypeDto loanTypeDto, Pageable pageable) {
        Page<LoanTypeEntity> entityPage = repository.findAll(pageable);
        List<LoanTypeEntity> entities = entityPage.getContent();
        return new PageImpl<>(loanTypeMapper.toDto(entities), pageable, entityPage.getTotalElements());
    }

    public LoanTypeDto update(LoanTypeDto loanTypeDto, Long id) {
        LoanTypeDto data = findById(id);
        LoanTypeEntity entity = loanTypeMapper.toEntity(loanTypeDto);
        BeanUtils.copyProperties(data, entity);
        return save(loanTypeMapper.toDto(entity));
    }
}

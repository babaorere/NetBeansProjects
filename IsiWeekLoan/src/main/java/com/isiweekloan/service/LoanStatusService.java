package com.isiweekloan.service;

import com.isiweekloan.entity.LoanContractEntity;
import com.isiweekloan.exception.ResourceNotFoundException;
import com.isiweekloan.dto.LoanStatusDto;
import com.isiweekloan.entity.LoanStatusEntity;
import com.isiweekloan.mapper.LoanStatusMapper;
import com.isiweekloan.repository.LoanStatusRepository;
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
public class LoanStatusService {
    private final LoanStatusRepository repository;
    private final LoanStatusMapper loanStatusMapper;

    public LoanStatusService(LoanStatusRepository repository, LoanStatusMapper loanStatusMapper) {
        this.repository = repository;
        this.loanStatusMapper = loanStatusMapper;
    }

    public LoanStatusDto save(LoanStatusDto loanStatusDto) {
        LoanStatusEntity entity = loanStatusMapper.toEntity(loanStatusDto);
        return loanStatusMapper.toDto(repository.save(entity));
    }

    public void deleteById(Long id) {
        repository.deleteById(id);
    }

    public LoanStatusDto findById(Long id) {
        try {
            return loanStatusMapper.toDto(repository.findById(id)
                    .orElseThrow(() -> new ResourceNotFoundException("Could not find")));
        } catch(Exception e) {
            return null;
        }
    }

    public Page<LoanStatusDto> findByCondition(LoanStatusDto loanStatusDto, Pageable pageable) {
        Page<LoanStatusEntity> entityPage = repository.findAll(pageable);
        List<LoanStatusEntity> entities = entityPage.getContent();
        return new PageImpl<>(loanStatusMapper.toDto(entities), pageable, entityPage.getTotalElements());
    }

    public LoanStatusDto update(LoanStatusDto loanStatusDto, Long id) {
        LoanStatusDto data = findById(id);
        LoanStatusEntity entity = loanStatusMapper.toEntity(loanStatusDto);
        BeanUtils.copyProperties(data, entity);
        return save(loanStatusMapper.toDto(entity));
    }
}

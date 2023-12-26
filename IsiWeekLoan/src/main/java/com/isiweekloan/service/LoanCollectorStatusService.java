package com.isiweekloan.service;

import com.isiweekloan.dto.LoanCollectorStatusDto;
import com.isiweekloan.entity.LoanCollectorStatusEntity;
import com.isiweekloan.entity.LoanCollectorEntity;
import com.isiweekloan.exception.ResourceNotFoundException;
import com.isiweekloan.mapper.LoanCollectorStatusMapper;
import com.isiweekloan.repository.LoanCollectorStatusRepository;
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
public class LoanCollectorStatusService {
    private final LoanCollectorStatusRepository repository;
    private final LoanCollectorStatusMapper loanCollectorStatusMapper;

    public LoanCollectorStatusService(LoanCollectorStatusRepository repository, LoanCollectorStatusMapper loanCollectorStatusMapper) {
        this.repository = repository;
        this.loanCollectorStatusMapper = loanCollectorStatusMapper;
    }

    public LoanCollectorStatusDto save(LoanCollectorStatusDto loanCollectorStatusDto) {
        LoanCollectorStatusEntity entity = loanCollectorStatusMapper.toEntity(loanCollectorStatusDto);
        return loanCollectorStatusMapper.toDto(repository.save(entity));
    }

    public void deleteById(Long id) {
        repository.deleteById(id);
    }

    public LoanCollectorStatusDto findById(Long id) {
        try {
            return loanCollectorStatusMapper.toDto(repository.findById(id)
                    .orElseThrow(() -> new ResourceNotFoundException("Could not find")));
        } catch (Exception e) {
            return null;
        }
    }

    public Page<LoanCollectorStatusDto> findByCondition(LoanCollectorStatusDto loanCollectorStatusDto, Pageable pageable) {
        Page<LoanCollectorStatusEntity> entityPage = repository.findAll(pageable);
        List<LoanCollectorStatusEntity> entities = entityPage.getContent();
        return new PageImpl<>(loanCollectorStatusMapper.toDto(entities), pageable, entityPage.getTotalElements());
    }

    public LoanCollectorStatusDto update(LoanCollectorStatusDto loanCollectorStatusDto, Long id) {
        LoanCollectorStatusDto data = findById(id);
        LoanCollectorStatusEntity entity = loanCollectorStatusMapper.toEntity(loanCollectorStatusDto);
        BeanUtils.copyProperties(data, entity);
        return save(loanCollectorStatusMapper.toDto(entity));
    }
}

package com.isiweekloan.service;

import com.isiweekloan.dto.LoanCollectorDto;
import com.isiweekloan.entity.LoanCollectorEntity;
import com.isiweekloan.entity.PersonEntity;
import com.isiweekloan.exception.ResourceNotFoundException;
import com.isiweekloan.mapper.LoanCollectorMapper;
import com.isiweekloan.repository.LoanCollectorRepository;
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
public class LoanCollectorService {
    private final LoanCollectorRepository repository;
    private final LoanCollectorMapper loanCollectorMapper;

    public LoanCollectorService(LoanCollectorRepository repository, LoanCollectorMapper loanCollectorMapper) {
        this.repository = repository;
        this.loanCollectorMapper = loanCollectorMapper;
    }

    public LoanCollectorDto save(LoanCollectorDto loanCollectorDto) {
        LoanCollectorEntity entity = loanCollectorMapper.toEntity(loanCollectorDto);
        return loanCollectorMapper.toDto(repository.save(entity));
    }

    public void deleteById(Long id) {
        repository.deleteById(id);
    }

    public LoanCollectorDto findById(Long id) {
        try {
            return loanCollectorMapper.toDto(repository.findById(id)
                    .orElseThrow(() -> new ResourceNotFoundException("Could not find")));
        } catch (Exception e) {
            return null;
        }
    }

    public Page<LoanCollectorDto> findByCondition(LoanCollectorDto loanCollectorDto, Pageable pageable) {
        Page<LoanCollectorEntity> entityPage = repository.findAll(pageable);
        List<LoanCollectorEntity> entities = entityPage.getContent();
        return new PageImpl<>(loanCollectorMapper.toDto(entities), pageable, entityPage.getTotalElements());
    }

    public LoanCollectorDto update(LoanCollectorDto loanCollectorDto, Long id) {
        LoanCollectorDto data = findById(id);
        LoanCollectorEntity entity = loanCollectorMapper.toEntity(loanCollectorDto);
        BeanUtils.copyProperties(data, entity);
        return save(loanCollectorMapper.toDto(entity));
    }
}

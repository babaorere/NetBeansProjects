package com.isiweekloan.service;

import com.isiweekloan.dto.LoanContractDto;
import com.isiweekloan.entity.LoanContractEntity;
import com.isiweekloan.exception.ResourceNotFoundException;
import com.isiweekloan.mapper.LoanContractMapper;
import com.isiweekloan.repository.LoanContractRepository;
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
public class LoanContractService {
    private final LoanContractRepository repository;
    private final LoanContractMapper loanContractMapper;

    public LoanContractService(LoanContractRepository repository, LoanContractMapper loanContractMapper) {
        this.repository = repository;
        this.loanContractMapper = loanContractMapper;
    }

    public LoanContractDto save(LoanContractDto loanContractDto) {
        LoanContractEntity entity = loanContractMapper.toEntity(loanContractDto);
        return loanContractMapper.toDto(repository.save(entity));
    }

    public void deleteById(long id) {
        repository.deleteById(id);
    }

    public LoanContractDto findById(long id) {
        try {
        return loanContractMapper.toDto(repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Not found")));
    } catch (Exception e) {
        return null;
    }

}

    public Page<LoanContractDto> findByCondition(LoanContractDto loanContractDto, Pageable pageable) {
        Page<LoanContractEntity> entityPage = repository.findAll(pageable);
        List<LoanContractEntity> entities = entityPage.getContent();
        return new PageImpl<>(loanContractMapper.toDto(entities), pageable, entityPage.getTotalElements());
    }

    public LoanContractDto update(LoanContractDto loanContractDto, long id) {
        LoanContractDto data = findById(id);
        LoanContractEntity entity = loanContractMapper.toEntity(loanContractDto);
        BeanUtils.copyProperties(data, entity);
        return save(loanContractMapper.toDto(entity));
    }
}

package com.isiweekloan.service;

import com.isiweekloan.dto.CriminalRecordDto;
import com.isiweekloan.entity.PersonEntity;
import com.isiweekloan.entity.CriminalRecordEntity;
import com.isiweekloan.exception.ResourceNotFoundException;
import com.isiweekloan.mapper.CriminalRecordMapper;
import com.isiweekloan.repository.CriminalRecordRepository;
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
public class CriminalRecordService {
    private final CriminalRecordRepository repository;
    private final CriminalRecordMapper criminalRecordMapper;

    public CriminalRecordService(CriminalRecordRepository repository, CriminalRecordMapper criminalRecordMapper) {
        this.repository = repository;
        this.criminalRecordMapper = criminalRecordMapper;
    }

    public CriminalRecordDto save(CriminalRecordDto criminalRecordDto) {
        CriminalRecordEntity entity = criminalRecordMapper.toEntity(criminalRecordDto);
        return criminalRecordMapper.toDto(repository.save(entity));
    }

    public void deleteById(Long id) {
        repository.deleteById(id);
    }

    public CriminalRecordDto findById(Long id) {
        try {
            return criminalRecordMapper.toDto(repository.findById(id)
                    .orElseThrow(() -> new ResourceNotFoundException("Could not find")));
        } catch (Exception e) {
            return null;
        }
    }

    public Page<CriminalRecordDto> findByCondition(CriminalRecordDto criminalRecordDto, Pageable pageable) {
        Page<CriminalRecordEntity> entityPage = repository.findAll(pageable);
        List<CriminalRecordEntity> entities = entityPage.getContent();
        return new PageImpl<>(criminalRecordMapper.toDto(entities), pageable, entityPage.getTotalElements());
    }

    public CriminalRecordDto update(CriminalRecordDto criminalRecordDto, Long id) {
        CriminalRecordDto data = findById(id);
        CriminalRecordEntity entity = criminalRecordMapper.toEntity(criminalRecordDto);
        BeanUtils.copyProperties(data, entity);
        return save(criminalRecordMapper.toDto(entity));
    }
}

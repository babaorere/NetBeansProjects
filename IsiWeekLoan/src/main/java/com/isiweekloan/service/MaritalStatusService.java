package com.isiweekloan.service;

import com.isiweekloan.dto.MaritalStatusDto;
import com.isiweekloan.entity.MaritalStatusEntity;
import com.isiweekloan.entity.PersonEntity;
import com.isiweekloan.exception.ResourceNotFoundException;
import com.isiweekloan.mapper.MaritalStatusMapper;
import com.isiweekloan.repository.MaritalStatusRepository;
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
public class MaritalStatusService {
    private final MaritalStatusRepository repository;
    private final MaritalStatusMapper maritalStatusMapper;

    public MaritalStatusService(MaritalStatusRepository repository, MaritalStatusMapper maritalStatusMapper) {
        this.repository = repository;
        this.maritalStatusMapper = maritalStatusMapper;
    }

    public MaritalStatusDto save(MaritalStatusDto maritalStatusDto) {
        MaritalStatusEntity entity = maritalStatusMapper.toEntity(maritalStatusDto);
        return maritalStatusMapper.toDto(repository.save(entity));
    }

    public void deleteById(Long id) {
        repository.deleteById(id);
    }

    public MaritalStatusDto findById(Long id) {
        try {
            return maritalStatusMapper.toDto(repository.findById(id)
                    .orElseThrow(() -> new ResourceNotFoundException("Could not find")));
        } catch (Exception e) {
            return null;
        }
    }

    public Page<MaritalStatusDto> findByCondition(MaritalStatusDto maritalStatusDto, Pageable pageable) {
        Page<MaritalStatusEntity> entityPage = repository.findAll(pageable);
        List<MaritalStatusEntity> entities = entityPage.getContent();
        return new PageImpl<>(maritalStatusMapper.toDto(entities), pageable, entityPage.getTotalElements());
    }

    public MaritalStatusDto update(MaritalStatusDto maritalStatusDto, Long id) {
        MaritalStatusDto data = findById(id);
        MaritalStatusEntity entity = maritalStatusMapper.toEntity(maritalStatusDto);
        BeanUtils.copyProperties(data, entity);
        return save(maritalStatusMapper.toDto(entity));
    }
}

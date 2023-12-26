package com.isiweekloan.service;

import com.isiweekloan.dto.CountryDto;
import com.isiweekloan.entity.CountryEntity;
import com.isiweekloan.entity.PersonEntity;
import com.isiweekloan.exception.ResourceNotFoundException;
import com.isiweekloan.mapper.CountryMapper;
import com.isiweekloan.repository.CountryRepository;
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
public class CountryService {
    private final CountryRepository repository;
    private final CountryMapper countryMapper;

    public CountryService(CountryRepository repository, CountryMapper countryMapper) {
        this.repository = repository;
        this.countryMapper = countryMapper;
    }

    public CountryDto save(CountryDto countryDto) {
        CountryEntity entity = countryMapper.toEntity(countryDto);
        return countryMapper.toDto(repository.save(entity));
    }

    public void deleteById(Long id) {
        repository.deleteById(id);
    }
    public CountryDto findById(Long id) {
        try {
            // Suponiendo que id ya es el ID del país y no necesitas iterar sobre él.
            return countryMapper.toDto(repository.findById(id)
                    .orElseThrow(() -> new ResourceNotFoundException("Could not find country with ID: " + id)));
        } catch (Exception e) {
            log.error("Error finding country with ID {}: {}", id, e.getMessage());
            return null;
        }
    }


    public Page<CountryDto> findByCondition(CountryDto countryDto, Pageable pageable) {
        Page<CountryEntity> entityPage = repository.findAll(pageable);
        List<CountryEntity> entities = entityPage.getContent();
        return new PageImpl<>(countryMapper.toDto(entities), pageable, entityPage.getTotalElements());
    }

    public CountryDto update(CountryDto countryDto, Long id) {
        CountryDto data = findById(id);
        CountryEntity entity = countryMapper.toEntity(countryDto);
        BeanUtils.copyProperties(data, entity);
        return save(countryMapper.toDto(entity));
    }
}

package com.isiweekloan.service;

import com.isiweekloan.dto.PersonDto;
import com.isiweekloan.entity.PersonEntity;
import com.isiweekloan.exception.ResourceNotFoundException;
import com.isiweekloan.mapper.PersonMapper;
import com.isiweekloan.repository.PersonRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Date;
import java.util.List;
import java.util.Optional;

@Slf4j
@Service
@Transactional
public class PersonService {
    private final PersonRepository repository;
    private final PersonMapper personMapper;

    public PersonService(PersonRepository repository, PersonMapper personMapper) {
        this.repository = repository;
        this.personMapper = personMapper;
    }

    public PersonDto save(PersonDto personDto) {
        PersonEntity entity = personMapper.toEntity(personDto);
        return personMapper.toDto(repository.save(entity));
    }

    public void deleteById(Long id) {
        repository.deleteById(id);
    }

    public PersonDto findById(Long id) {
        try {
            return personMapper.toDto(repository.findById(id)
                    .orElseThrow(() -> new ResourceNotFoundException("Could not find")));
        } catch (Exception e) {
            return null;
        }
    }

    public Page<PersonDto> findByCondition(PersonDto personDto, Pageable pageable) {
        Page<PersonEntity> entityPage = repository.findAll(pageable);
        List<PersonEntity> entities = entityPage.getContent();
        return new PageImpl<>(personMapper.toDto(entities), pageable, entityPage.getTotalElements());
    }

    public PersonDto update(PersonDto personDto, Long id) {
        PersonDto data = findById(id);
        PersonEntity entity = personMapper.toEntity(personDto);
        BeanUtils.copyProperties(data, entity);
        return save(personMapper.toDto(entity));
    }
}

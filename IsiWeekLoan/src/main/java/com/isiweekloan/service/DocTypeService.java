package com.isiweekloan.service;

import com.isiweekloan.exception.ResourceNotFoundException;
import com.isiweekloan.dto.DocTypeDto;
import com.isiweekloan.entity.DocTypeEntity;
import com.isiweekloan.entity.PersonEntity;
import com.isiweekloan.mapper.DocTypeMapper;
import com.isiweekloan.repository.DocTypeRepository;
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
public class DocTypeService {
    private final DocTypeRepository repository;
    private final DocTypeMapper docTypeMapper;

    public DocTypeService(DocTypeRepository repository, DocTypeMapper docTypeMapper) {
        this.repository = repository;
        this.docTypeMapper = docTypeMapper;
    }

    public DocTypeDto save(DocTypeDto docTypeDto) {
        DocTypeEntity entity = docTypeMapper.toEntity(docTypeDto);
        return docTypeMapper.toDto(repository.save(entity));
    }

    public void deleteById(Long id) {
        repository.deleteById(id);
    }

    public DocTypeDto findById(Long id) {
        try {
            return docTypeMapper.toDto(repository.findById(id)
                    .orElseThrow(() -> new ResourceNotFoundException("Could not find")));
        } catch(Exception e) {
            return null;
        }
    }

    public Page<DocTypeDto> findByCondition(DocTypeDto docTypeDto, Pageable pageable) {
        Page<DocTypeEntity> entityPage = repository.findAll(pageable);
        List<DocTypeEntity> entities = entityPage.getContent();
        return new PageImpl<>(docTypeMapper.toDto(entities), pageable, entityPage.getTotalElements());
    }

    public DocTypeDto update(DocTypeDto docTypeDto, Long id) {
        DocTypeDto data = findById(id);
        DocTypeEntity entity = docTypeMapper.toEntity(docTypeDto);
        BeanUtils.copyProperties(data, entity);
        return save(docTypeMapper.toDto(entity));
    }
}

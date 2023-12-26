package com.isiweekloan.service;

import com.isiweekloan.dto.PhoneNotificationDto;
import com.isiweekloan.entity.PhoneNotificationEntity;
import com.isiweekloan.exception.ResourceNotFoundException;
import com.isiweekloan.mapper.PhoneNotificationMapper;
import com.isiweekloan.repository.PhoneNotificationRepository;
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
public class PhoneNotificationService {
    private final PhoneNotificationRepository repository;
    private final PhoneNotificationMapper phoneNotificationMapper;

    public PhoneNotificationService(PhoneNotificationRepository repository, PhoneNotificationMapper phoneNotificationMapper) {
        this.repository = repository;
        this.phoneNotificationMapper = phoneNotificationMapper;
    }

    public PhoneNotificationDto save(PhoneNotificationDto phoneNotificationDto) {
        PhoneNotificationEntity entity = phoneNotificationMapper.toEntity(phoneNotificationDto);
        return phoneNotificationMapper.toDto(repository.save(entity));
    }

    public void deleteById(Long id) {
        repository.deleteById(id);
    }

    public PhoneNotificationDto findById(Long id) {
        try {
            return phoneNotificationMapper.toDto(repository.findById(id)
                    .orElseThrow(() -> new ResourceNotFoundException("Could not find")));
        } catch (Exception e) {
            return null;
        }
    }

    public Page<PhoneNotificationDto> findByCondition(PhoneNotificationDto phoneNotificationDto, Pageable pageable) {
        Page<PhoneNotificationEntity> entityPage = repository.findAll(pageable);
        List<PhoneNotificationEntity> entities = entityPage.getContent();
        return new PageImpl<>(phoneNotificationMapper.toDto(entities), pageable, entityPage.getTotalElements());
    }

    public PhoneNotificationDto update(PhoneNotificationDto phoneNotificationDto, Long id) {
        PhoneNotificationDto data = findById(id);
        PhoneNotificationEntity entity = phoneNotificationMapper.toEntity(phoneNotificationDto);
        BeanUtils.copyProperties(data, entity);
        return save(phoneNotificationMapper.toDto(entity));
    }
}

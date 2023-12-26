package com.isiweekloan.service;

import com.isiweekloan.dto.EmailNotificationDto;
import com.isiweekloan.entity.EmailNotificationEntity;
import com.isiweekloan.exception.ResourceNotFoundException;
import com.isiweekloan.mapper.EmailNotificationMapper;
import com.isiweekloan.repository.EmailNotificationRepository;
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
public class EmailNotificationService {
    private final EmailNotificationRepository repository;
    private final EmailNotificationMapper emailNotificationMapper;

    public EmailNotificationService(EmailNotificationRepository repository, EmailNotificationMapper emailNotificationMapper) {
        this.repository = repository;
        this.emailNotificationMapper = emailNotificationMapper;
    }

    public EmailNotificationDto save(EmailNotificationDto emailNotificationDto) {
        EmailNotificationEntity entity = emailNotificationMapper.toEntity(emailNotificationDto);
        return emailNotificationMapper.toDto(repository.save(entity));
    }

    public void deleteById(Long id) {
        repository.deleteById(id);
    }

    public EmailNotificationDto findById(Long id) {
        try {
            return emailNotificationMapper.toDto(repository.findById(id)
                    .orElseThrow(() -> new ResourceNotFoundException("Could not find")));
        } catch(Exception e) {
            return null;
        }
    }

    public Page<EmailNotificationDto> findByCondition(EmailNotificationDto emailNotificationDto, Pageable pageable) {
        Page<EmailNotificationEntity> entityPage = repository.findAll(pageable);
        List<EmailNotificationEntity> entities = entityPage.getContent();
        return new PageImpl<>(emailNotificationMapper.toDto(entities), pageable, entityPage.getTotalElements());
    }

    public EmailNotificationDto update(EmailNotificationDto emailNotificationDto, Long id) {
        EmailNotificationDto data = findById(id);
        EmailNotificationEntity entity = emailNotificationMapper.toEntity(emailNotificationDto);
        BeanUtils.copyProperties(data, entity);
        return save(emailNotificationMapper.toDto(entity));
    }
}

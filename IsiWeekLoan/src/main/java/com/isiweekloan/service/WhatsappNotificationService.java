package com.isiweekloan.service;

import com.isiweekloan.dto.WhatsappNotificationDto;
import com.isiweekloan.entity.WhatsappNotificationEntity;
import com.isiweekloan.exception.ResourceNotFoundException;
import com.isiweekloan.mapper.WhatsappNotificationMapper;
import com.isiweekloan.repository.WhatsappNotificationRepository;
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
public class WhatsappNotificationService {
    private final WhatsappNotificationRepository repository;
    private final WhatsappNotificationMapper whatsappNotificationMapper;

    public WhatsappNotificationService(WhatsappNotificationRepository repository, WhatsappNotificationMapper whatsappNotificationMapper) {
        this.repository = repository;
        this.whatsappNotificationMapper = whatsappNotificationMapper;
    }

    public WhatsappNotificationDto save(WhatsappNotificationDto whatsappNotificationDto) {
        WhatsappNotificationEntity entity = whatsappNotificationMapper.toEntity(whatsappNotificationDto);
        return whatsappNotificationMapper.toDto(repository.save(entity));
    }

    public void deleteById(Long id) {
        repository.deleteById(id);
    }

    public WhatsappNotificationDto findById(Long id) {
        try {
            return whatsappNotificationMapper.toDto(repository.findById(id)
                    .orElseThrow(() -> new ResourceNotFoundException("Could not find WhatsappNotification with ID: " + id)));
        } catch (Exception e) {
            log.error("Error finding WhatsappNotification with ID {}: {}", id, e.getMessage());
            // Puedes lanzar una excepción diferente o manejarla de otra manera según tus requisitos.
            return null;
        }
    }


    public Page<WhatsappNotificationDto> findByCondition(WhatsappNotificationDto whatsappNotificationDto, Pageable pageable) {
        Page<WhatsappNotificationEntity> entityPage = repository.findAll(pageable);
        List<WhatsappNotificationEntity> entities = entityPage.getContent();
        return new PageImpl<>(whatsappNotificationMapper.toDto(entities), pageable, entityPage.getTotalElements());
    }

    public WhatsappNotificationDto update(WhatsappNotificationDto whatsappNotificationDto, Long id) {
        WhatsappNotificationDto data = findById(id);
        WhatsappNotificationEntity entity = whatsappNotificationMapper.toEntity(whatsappNotificationDto);
        BeanUtils.copyProperties(data, entity);
        return save(whatsappNotificationMapper.toDto(entity));
    }
}

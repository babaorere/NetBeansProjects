package com.isiweekloan.service;

import com.isiweekloan.dto.PaymentTypeDto;
import com.isiweekloan.entity.PaymentDatailsEntity;
import com.isiweekloan.entity.PaymentTypeEntity;
import com.isiweekloan.exception.ResourceNotFoundException;
import com.isiweekloan.mapper.PaymentTypeMapper;
import com.isiweekloan.repository.PaymentTypeRepository;
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
public class PaymentTypeService {
    private final PaymentTypeRepository repository;
    private final PaymentTypeMapper paymentTypeMapper;

    public PaymentTypeService(PaymentTypeRepository repository, PaymentTypeMapper paymentTypeMapper) {
        this.repository = repository;
        this.paymentTypeMapper = paymentTypeMapper;
    }

    public PaymentTypeDto save(PaymentTypeDto paymentTypeDto) {
        PaymentTypeEntity entity = paymentTypeMapper.toEntity(paymentTypeDto);
        return paymentTypeMapper.toDto(repository.save(entity));
    }

    public void deleteById(Long id) {
        repository.deleteById(id);
    }

    public PaymentTypeDto findById(Long id) {
        try {
        return paymentTypeMapper.toDto(repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Could not find")));
    } catch (Exception e) {
        return null;
    }
    }

    public Page<PaymentTypeDto> findByCondition(PaymentTypeDto paymentTypeDto, Pageable pageable) {
        Page<PaymentTypeEntity> entityPage = repository.findAll(pageable);
        List<PaymentTypeEntity> entities = entityPage.getContent();
        return new PageImpl<>(paymentTypeMapper.toDto(entities), pageable, entityPage.getTotalElements());
    }

    public PaymentTypeDto update(PaymentTypeDto paymentTypeDto, Long id) {
        PaymentTypeDto data = findById(id);
        PaymentTypeEntity entity = paymentTypeMapper.toEntity(paymentTypeDto);
        BeanUtils.copyProperties(data, entity);
        return save(paymentTypeMapper.toDto(entity));
    }
}

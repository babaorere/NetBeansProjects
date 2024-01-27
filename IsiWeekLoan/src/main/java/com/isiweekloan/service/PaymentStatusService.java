package com.isiweekloan.service;

import com.isiweekloan.dto.PaymentStatusDto;
import com.isiweekloan.entity.PaymentDatailsEntity;
import com.isiweekloan.entity.PaymentStatusEntity;
import com.isiweekloan.exception.ResourceNotFoundException;
import com.isiweekloan.mapper.PaymentStatusMapper;
import com.isiweekloan.repository.PaymentStatusRepository;
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
public class PaymentStatusService {
    private final PaymentStatusRepository repository;
    private final PaymentStatusMapper paymentStatusMapper;

    public PaymentStatusService(PaymentStatusRepository repository, PaymentStatusMapper paymentStatusMapper) {
        this.repository = repository;
        this.paymentStatusMapper = paymentStatusMapper;
    }

    public PaymentStatusDto save(PaymentStatusDto paymentStatusDto) {
        PaymentStatusEntity entity = paymentStatusMapper.toEntity(paymentStatusDto);
        return paymentStatusMapper.toDto(repository.save(entity));
    }

    public void deleteById(Long id) {
        repository.deleteById(id);
    }

    public PaymentStatusDto findById(Long id) {
        try {
            return paymentStatusMapper.toDto(repository.findById(id)
                    .orElseThrow(() -> new ResourceNotFoundException("Could not find")));
        } catch (Exception e) {
            return null;
        }
    }

    public Page<PaymentStatusDto> findByCondition(PaymentStatusDto paymentStatusDto, Pageable pageable) {
        Page<PaymentStatusEntity> entityPage = repository.findAll(pageable);
        List<PaymentStatusEntity> entities = entityPage.getContent();
        return new PageImpl<>(paymentStatusMapper.toDto(entities), pageable, entityPage.getTotalElements());
    }

    public PaymentStatusDto update(PaymentStatusDto paymentStatusDto, Long id) {
        PaymentStatusDto data = findById(id);
        PaymentStatusEntity entity = paymentStatusMapper.toEntity(paymentStatusDto);
        BeanUtils.copyProperties(data, entity);
        return save(paymentStatusMapper.toDto(entity));
    }
}

package com.isiweekloan.service;

import com.isiweekloan.dto.PaymentDetailsDto;
import com.isiweekloan.entity.PaymentDatailsEntity;
import com.isiweekloan.exception.ResourceNotFoundException;
import com.isiweekloan.mapper.PaymentDatailsMapper;
import com.isiweekloan.repository.PaymentDatailsRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Date;
import java.util.List;

@Slf4j
@Service
@Transactional
public class PaymentDetailsService {
    private final PaymentDatailsRepository repository;
    private final PaymentDatailsMapper paymentDatailsMapper;

    public PaymentDetailsService(PaymentDatailsRepository repository, PaymentDatailsMapper paymentDatailsMapper) {
        this.repository = repository;
        this.paymentDatailsMapper = paymentDatailsMapper;
    }

    public PaymentDetailsDto save(PaymentDetailsDto paymentDatailsDto) {
        PaymentDatailsEntity entity = paymentDatailsMapper.toEntity(paymentDatailsDto);
        return paymentDatailsMapper.toDto(repository.save(entity));
    }

    public void deleteById(Long id) {
        repository.deleteById(id);
    }

    public PaymentDetailsDto findById(Long id) {
        try {
            return paymentDatailsMapper.toDto(repository.findById(id)
                    .orElseThrow(() -> new ResourceNotFoundException("Not found")));
        } catch (Exception e) {
            return null;
        }
    }

    public Page<PaymentDetailsDto> findByCondition(PaymentDetailsDto paymentDatailsDto, Pageable pageable) {
        Page<PaymentDatailsEntity> entityPage = repository.findAll(pageable);
        List<PaymentDatailsEntity> entities = entityPage.getContent();
        return new PageImpl<>(paymentDatailsMapper.toDto(entities), pageable, entityPage.getTotalElements());
    }

    public PaymentDetailsDto update(PaymentDetailsDto paymentDatailsDto, Long id) {
        PaymentDetailsDto data = findById(id);
        PaymentDatailsEntity entity = paymentDatailsMapper.toEntity(paymentDatailsDto);
        BeanUtils.copyProperties(data, entity);
        return save(paymentDatailsMapper.toDto(entity));
    }
}

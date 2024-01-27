package com.isiweekloan.service;

import com.isiweekloan.dto.CustomerDto;
import com.isiweekloan.entity.CustomerEntity;
import com.isiweekloan.exception.ResourceNotFoundException;
import com.isiweekloan.mapper.CustomerMapper;
import com.isiweekloan.repository.CustomerRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Slf4j
@Service
@Transactional
public class CustomerService {
    private final CustomerRepository repository;
    private final CustomerMapper customerMapper;

    public CustomerService(CustomerRepository repository, CustomerMapper customerMapper) {
        this.repository = repository;
        this.customerMapper = customerMapper;
    }

    public CustomerDto save(CustomerDto customerDto) {
        CustomerEntity entity = customerMapper.toEntity(customerDto);
        return customerMapper.toDto(repository.save(entity));
    }

    public void deleteById(Long id) {
        repository.deleteById(id);
    }

    public CustomerDto findById(Long id) {
        try {
            return customerMapper.toDto(repository.findById(id)
                    .orElseThrow(() -> new ResourceNotFoundException("Could not find")));
        } catch (Exception e) {
            return null;
        }
    }

    public Page<CustomerDto> findByCondition(CustomerDto customerDto, Pageable pageable) {
        Page<CustomerEntity> entityPage = repository.findAll(pageable);
        List<CustomerEntity> entities = entityPage.getContent();
        return new PageImpl<>(customerMapper.toDto(entities), pageable, entityPage.getTotalElements());
    }

    public CustomerDto update(CustomerDto customerDto, Long id) {
        CustomerDto data = findById(id);
        CustomerEntity entity = customerMapper.toEntity(customerDto);
        BeanUtils.copyProperties(data, entity);
        return save(customerMapper.toDto(entity));
    }
}

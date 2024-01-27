package com.isiweekloan.mapper;

import com.isiweekloan.dto.CustomerDto;
import com.isiweekloan.entity.CustomerEntity;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface CustomerMapper extends EntityMapper<CustomerDto, CustomerEntity> {
}
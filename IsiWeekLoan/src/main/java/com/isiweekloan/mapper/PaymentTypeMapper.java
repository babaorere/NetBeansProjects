package com.isiweekloan.mapper;

import com.isiweekloan.dto.PaymentTypeDto;
import com.isiweekloan.entity.PaymentTypeEntity;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface PaymentTypeMapper extends EntityMapper<PaymentTypeDto, PaymentTypeEntity> {
}
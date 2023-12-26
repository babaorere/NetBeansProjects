package com.isiweekloan.mapper;

import com.isiweekloan.dto.PaymentDetailsDto;
import com.isiweekloan.entity.PaymentDatailsEntity;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface PaymentDatailsMapper extends EntityMapper<PaymentDetailsDto, PaymentDatailsEntity> {
}
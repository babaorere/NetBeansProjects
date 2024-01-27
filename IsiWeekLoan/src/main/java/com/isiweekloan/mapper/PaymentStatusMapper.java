package com.isiweekloan.mapper;

import com.isiweekloan.dto.PaymentStatusDto;
import com.isiweekloan.entity.PaymentStatusEntity;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface PaymentStatusMapper extends EntityMapper<PaymentStatusDto, PaymentStatusEntity> {
}
package com.isiweekloan.mapper;

import com.isiweekloan.dto.WhatsappNotificationDto;
import com.isiweekloan.entity.WhatsappNotificationEntity;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface WhatsappNotificationMapper extends EntityMapper<WhatsappNotificationDto, WhatsappNotificationEntity> {
}
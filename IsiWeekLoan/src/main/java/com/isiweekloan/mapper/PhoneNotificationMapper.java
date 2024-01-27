package com.isiweekloan.mapper;

import com.isiweekloan.dto.PhoneNotificationDto;
import com.isiweekloan.entity.PhoneNotificationEntity;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface PhoneNotificationMapper extends EntityMapper<PhoneNotificationDto, PhoneNotificationEntity> {
}
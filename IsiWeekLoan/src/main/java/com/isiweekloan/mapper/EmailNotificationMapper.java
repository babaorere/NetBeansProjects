package com.isiweekloan.mapper;

import com.isiweekloan.dto.EmailNotificationDto;
import com.isiweekloan.entity.EmailNotificationEntity;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface EmailNotificationMapper extends EntityMapper<EmailNotificationDto, EmailNotificationEntity> {
}
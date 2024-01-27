package com.isiweekloan.mapper;

import com.isiweekloan.dto.MaritalStatusDto;
import com.isiweekloan.entity.MaritalStatusEntity;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface MaritalStatusMapper extends EntityMapper<MaritalStatusDto, MaritalStatusEntity> {
}
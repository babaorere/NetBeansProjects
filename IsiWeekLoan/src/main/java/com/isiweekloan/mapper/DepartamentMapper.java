package com.isiweekloan.mapper;

import com.isiweekloan.dto.DepartamentDto;
import com.isiweekloan.entity.DepartamentEntity;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface DepartamentMapper extends EntityMapper<DepartamentDto, DepartamentEntity> {
}
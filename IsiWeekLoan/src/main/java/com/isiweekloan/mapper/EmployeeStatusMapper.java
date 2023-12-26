package com.isiweekloan.mapper;

import com.isiweekloan.dto.EmployeeStatusDto;
import com.isiweekloan.entity.EmployeeStatusEntity;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface EmployeeStatusMapper extends EntityMapper<EmployeeStatusDto, EmployeeStatusEntity> {
}
package com.isiweekloan.mapper;

import com.isiweekloan.dto.EmployeeDto;
import com.isiweekloan.entity.EmployeeEntity;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface EmployeeMapper extends EntityMapper<EmployeeDto, EmployeeEntity> {
}
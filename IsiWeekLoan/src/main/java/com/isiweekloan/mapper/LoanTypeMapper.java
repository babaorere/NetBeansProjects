package com.isiweekloan.mapper;

import com.isiweekloan.dto.LoanTypeDto;
import com.isiweekloan.entity.LoanTypeEntity;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface LoanTypeMapper extends EntityMapper<LoanTypeDto, LoanTypeEntity> {
}
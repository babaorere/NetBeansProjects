package com.isiweekloan.mapper;

import com.isiweekloan.dto.LoanStatusDto;
import com.isiweekloan.entity.LoanStatusEntity;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface LoanStatusMapper extends EntityMapper<LoanStatusDto, LoanStatusEntity> {
}
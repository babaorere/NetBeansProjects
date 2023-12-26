package com.isiweekloan.mapper;

import com.isiweekloan.dto.LoanContractDto;
import com.isiweekloan.entity.LoanContractEntity;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface LoanContractMapper extends EntityMapper<LoanContractDto, LoanContractEntity> {
}
package com.isiweekloan.mapper;

import com.isiweekloan.dto.LoanCollectorStatusDto;
import com.isiweekloan.entity.LoanCollectorStatusEntity;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface LoanCollectorStatusMapper extends EntityMapper<LoanCollectorStatusDto, LoanCollectorStatusEntity> {
}
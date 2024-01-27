package com.isiweekloan.mapper;

import com.isiweekloan.dto.LoanCollectorDto;
import com.isiweekloan.entity.LoanCollectorEntity;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface LoanCollectorMapper extends EntityMapper<LoanCollectorDto, LoanCollectorEntity> {
}
package com.isiweekloan.mapper;

import com.isiweekloan.dto.CriminalRecordDto;
import com.isiweekloan.entity.CriminalRecordEntity;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface CriminalRecordMapper extends EntityMapper<CriminalRecordDto, CriminalRecordEntity> {
}
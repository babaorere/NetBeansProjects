package com.isiweek.company;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface CompanyStatusMapper {

    CompanyStatusMapper INSTANCE = Mappers.getMapper(CompanyStatusMapper.class);

    CompanyStatusDTO mapToDTO(CompanyStatus companyStatus);

    CompanyStatus mapToEntity(CompanyStatusDTO companyStatusDTO);
}

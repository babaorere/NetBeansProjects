package com.isiweekloan.mapper;

import com.isiweekloan.dto.CompanyDto;
import com.isiweekloan.entity.CompanyEntity;

@Mapper(componentModel = "spring")
public interface CompanyMapper extends EntityMapper<CompanyDto, CompanyEntity> {
}
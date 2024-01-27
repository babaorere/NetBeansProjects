package com.isiweekloan.mapper;

import com.isiweekloan.dto.CountryDto;
import com.isiweekloan.entity.CountryEntity;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface CountryMapper extends EntityMapper<CountryDto, CountryEntity> {
}
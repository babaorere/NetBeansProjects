package com.isiweekloan.mapper;

import com.isiweekloan.dto.PersonDto;
import com.isiweekloan.entity.PersonEntity;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface PersonMapper extends EntityMapper<PersonDto, PersonEntity> {
}
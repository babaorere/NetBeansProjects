package com.isiweekloan.mapper;

import com.isiweekloan.dto.DocTypeDto;
import com.isiweekloan.entity.DocTypeEntity;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface DocTypeMapper extends EntityMapper<DocTypeDto, DocTypeEntity> {
}
package com.isiweekloan.mapper;

import com.isiweekloan.dto.JobTitleDto;
import com.isiweekloan.entity.JobTitleEntity;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface JobTitleMapper extends EntityMapper<JobTitleDto, JobTitleEntity> {
}
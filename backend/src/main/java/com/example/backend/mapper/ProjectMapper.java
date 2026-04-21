package com.example.backend.mapper;

import com.example.backend.dto.ProjectDto;
import com.example.backend.model.Project;
import org.mapstruct.*;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = MappingConstants.ComponentModel.SPRING,
        uses = {SkillMapper.class})
public interface ProjectMapper {

    Project toEntity(ProjectDto projectDto);

    ProjectDto toDto(Project project);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    Project partialUpdate(ProjectDto projectDto, @MappingTarget Project project);
}

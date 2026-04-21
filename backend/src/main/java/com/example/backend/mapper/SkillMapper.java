package com.example.backend.mapper;

import com.example.backend.dto.SkillDto;
import com.example.backend.model.Skill;
import org.mapstruct.*;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = MappingConstants.ComponentModel.SPRING)
public interface SkillMapper {

    Skill toEntity(SkillDto skillDto);

    SkillDto toDto(Skill skill);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    Skill partialUpdate(SkillDto skillDto, @MappingTarget Skill skill);
}

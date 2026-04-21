package com.example.backend.mapper;

import com.example.backend.dto.CareerDto;
import com.example.backend.model.Career;
import org.mapstruct.*;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = MappingConstants.ComponentModel.SPRING)
public interface CareerMapper {

    Career toEntity(CareerDto careerDto);

    CareerDto toDto(Career career);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    Career partialUpdate(CareerDto careerDto, @MappingTarget Career career);
}

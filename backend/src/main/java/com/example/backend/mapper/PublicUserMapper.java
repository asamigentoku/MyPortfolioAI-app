package com.example.backend.mapper;

import com.example.backend.dto.PublicUserDto;
import com.example.backend.model.User;
import org.mapstruct.*;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = MappingConstants.ComponentModel.SPRING)
public interface PublicUserMapper {

    User toEntity(PublicUserDto userDto);

    PublicUserDto toDto(User user);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    User partialUpdate(PublicUserDto userDto, @MappingTarget User user);
}
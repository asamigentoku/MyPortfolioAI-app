package com.example.backend.mapper;

import com.example.backend.dto.LicenseDto;
import com.example.backend.model.License;
import org.mapstruct.*;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = MappingConstants.ComponentModel.SPRING)
public interface LicenseMapper {

    License toEntity(LicenseDto licenseDto);

    LicenseDto toDto(License license);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    License partialUpdate(LicenseDto licenseDto, @MappingTarget License license);
}

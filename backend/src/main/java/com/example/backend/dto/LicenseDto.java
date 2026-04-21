package com.example.backend.dto;

import lombok.Value;

import java.io.Serializable;
import java.time.LocalDate;

/**
 * DTO for {@link com.example.backend.model.License}
 */
@Value
public class LicenseDto implements Serializable {
    Integer id;
    String licenseName;
    LocalDate createdAt;
}
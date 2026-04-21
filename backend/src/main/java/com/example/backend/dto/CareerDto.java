package com.example.backend.dto;

import lombok.Value;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * DTO for {@link com.example.backend.model.Career}
 */
@Value
public class CareerDto implements Serializable {
    Integer id;
    String careerName;
    LocalDateTime time;
}
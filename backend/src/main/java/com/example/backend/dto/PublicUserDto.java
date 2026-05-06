package com.example.backend.dto;


import lombok.Value;

import java.io.Serializable;

/**
 * DTO for {@link com.example.backend.model.User}
 */
@Value
public class PublicUserDto implements Serializable {
    Integer id;
    String name;
    Boolean isPublic;
}


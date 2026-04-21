package com.example.backend.dto;

import lombok.Value;

import java.io.Serializable;

/**
 * DTO for {@link com.example.backend.model.User}
 */
@Value
public class UserDto implements Serializable {
    Integer id;
    String providerId;
    Integer githubId;
    String name;
    String email;
}
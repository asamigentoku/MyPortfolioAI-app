package com.example.backend.dto;

import lombok.Value;

import java.io.Serializable;

@Value
public class SettingDto implements Serializable {
    Boolean isPublic;
    String secretSlug;
}

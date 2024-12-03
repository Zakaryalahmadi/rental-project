package com.rental.rental.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.time.LocalDateTime;

public record UserDto(
        Long id,
        String name,
        String email,

        @JsonProperty("created_at")
        @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy/MM/dd")
        LocalDateTime createdAt,

        @JsonProperty("updated_at")
        @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy/MM/dd")
        LocalDateTime updatedAt
) {}
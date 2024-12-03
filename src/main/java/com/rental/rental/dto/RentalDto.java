package com.rental.rental.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.Column;
import lombok.Data;
import lombok.Getter;

import java.time.LocalDateTime;

public record RentalDto (
        Long id,
        String name,
        double surface,
        double price,
        String picture,
        String description,
        @JsonProperty("owner_id")
        Long ownerId,

        @JsonProperty("created_at")
        @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy/MM/dd")
        LocalDateTime createdAt,

        @JsonProperty("updated_at")
        @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy/MM/dd")
        LocalDateTime updatedAt
) {}

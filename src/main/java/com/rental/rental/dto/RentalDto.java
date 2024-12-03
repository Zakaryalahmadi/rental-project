package com.rental.rental.dto;

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
        LocalDateTime createdAt,

        @JsonProperty("updated_at")
        LocalDateTime updatedAt
) {}

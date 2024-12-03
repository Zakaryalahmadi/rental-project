package com.rental.rental.dto.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotNull;

public record MessageRequestDto(
        @NotNull(message = "Message content must not be null.")
        String message,
        @NotNull(message = "User ID must not be null.")
        @JsonProperty("user_id")
        Long userId,
        @NotNull(message = "Rental ID must not be null.")
        @JsonProperty("rental_id")
        Long rentalId
) {}
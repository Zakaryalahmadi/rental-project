package com.rental.rental.dto.request;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

public record UpdateRentalDto(
        @NotEmpty(message = "Name is mandatory and cannot be empty.")
        String name,

        @NotNull(message = "Surface is mandatory.")
        @DecimalMin(value = "0.0", message = "Surface must be non-negative.")
        Double surface,

        @NotNull(message = "Price is mandatory.")
        @DecimalMin(value = "0.0", message = "Price must be non-negative.")
        Double price,

        @NotEmpty(message = "Description is mandatory and cannot be empty.")
        String description
) {}

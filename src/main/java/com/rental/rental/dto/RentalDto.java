package com.rental.rental.dto;

import lombok.Data;
import lombok.Getter;

public record RentalDto (
        Long id,
        String name,
        double surface,
        double price,
        String picture,
        String description,
        Long ownerId
) {}

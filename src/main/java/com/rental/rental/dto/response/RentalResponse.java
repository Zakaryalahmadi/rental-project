package com.rental.rental.dto.response;

import com.rental.rental.dto.RentalDto;

import java.util.List;

public record RentalResponse(List<RentalDto> rentals) {}

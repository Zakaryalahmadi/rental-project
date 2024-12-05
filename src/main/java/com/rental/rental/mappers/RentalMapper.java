package com.rental.rental.mappers;


import com.rental.rental.dto.RentalDto;
import com.rental.rental.dto.request.CreateRentalDto;
import com.rental.rental.entities.Rental;
import com.rental.rental.entities.User;
import org.springframework.stereotype.Service;

@Service
public class RentalMapper {

    public RentalDto mapToDto(Rental rental) {
        return new RentalDto(
                rental.getId(),
                rental.getName(),
                rental.getSurface(),
                rental.getPrice(),
                rental.getPicture(),
                rental.getDescription(),
                rental.getOwner() != null ? rental.getOwner().getId() : null,
                rental.getCreatedAt(),
                rental.getCreatedAt()
        );
    }

    public Rental mapToEntity(CreateRentalDto dto, String pictureUrl, User owner) {
        Rental rental = new Rental();
        rental.setName(dto.name());
        rental.setSurface(dto.surface());
        rental.setPrice(dto.price());
        rental.setDescription(dto.description());
        rental.setPicture(pictureUrl);
        rental.setOwner(owner);
        return rental;
    }
}
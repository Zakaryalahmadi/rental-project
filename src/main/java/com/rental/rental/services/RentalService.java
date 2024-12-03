package com.rental.rental.services;

import com.rental.rental.dto.request.CreateRentalDto;
import com.rental.rental.dto.RentalDto;
import com.rental.rental.dto.RentalMapper;
import com.rental.rental.dto.request.UpdateRentalDto;
import com.rental.rental.dto.response.RentalResponse;
import com.rental.rental.entities.Rental;
import com.rental.rental.entities.User;
import com.rental.rental.exceptions.RentalNotFoundException;
import com.rental.rental.repositories.RentalRepository;
import com.rental.rental.repositories.UserRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RentalService {
    private final RentalRepository rentalRepository;

    private  final RentalMapper rentalMapper;

    private  final UserRepository userRepository;

    private final UserService userService;

    public RentalService(RentalRepository rentalRepository, RentalMapper rentalMapper, UserRepository userRepository, UserService userService){
        this.rentalRepository = rentalRepository;
        this.rentalMapper = rentalMapper;
        this.userRepository = userRepository;
        this.userService = userService;
    }

    public RentalResponse getAllRentals() {
        List<RentalDto> rentals = rentalRepository
                .findAll()
                .stream()
                .map(rentalMapper::mapToDto)
                .toList();

        return new RentalResponse(rentals);
    }

    public RentalDto getRentalById(Long id) {
        return rentalRepository
                .findById(id)
                .map(rentalMapper::mapToDto)
                .orElseThrow(
                        () -> new RentalNotFoundException("Rental with id " + id + " not found")
                );
    }

    public void createRental(CreateRentalDto createRentalDto, String pictureUrl) {
        User user = userRepository.findById(3L)
                .orElseThrow(() -> new RuntimeException("Utilisateur fictif non trouvé"));

        Rental rental = rentalMapper.mapToEntity(createRentalDto, pictureUrl, user);

        rentalRepository.save(rental);
    }

    public void updateRental(Long id, UpdateRentalDto updateRentalDto) {
        Rental rental = rentalRepository.findById(id)
                .orElseThrow(() -> new RentalNotFoundException("Rental with ID " + id + " not found"));

        rental.setName(updateRentalDto.name());
        rental.setSurface(updateRentalDto.surface());
        rental.setPrice(updateRentalDto.price());
        rental.setDescription(updateRentalDto.description());

        rentalRepository.save(rental);
    }
}

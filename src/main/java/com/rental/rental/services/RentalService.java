package com.rental.rental.services;

import com.rental.rental.dto.request.CreateRentalDto;
import com.rental.rental.dto.RentalDto;
import com.rental.rental.dto.RentalMapper;
import com.rental.rental.dto.response.RentalResponse;
import com.rental.rental.entities.Rental;
import com.rental.rental.entities.User;
import com.rental.rental.exceptions.NotFoundRentalException;
import com.rental.rental.repositories.RentalRepository;
import com.rental.rental.repositories.UserRepository;
import jakarta.validation.Valid;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.ModelAttribute;

import java.util.List;

@Service
public class RentalService {
    private final RentalRepository rentalRepository;

    private  final RentalMapper rentalMapper;

    private  final UserRepository userRepository;

    public RentalService(RentalRepository rentalRepository, RentalMapper rentalMapper, UserRepository userRepository){
        this.rentalRepository = rentalRepository;
        this.rentalMapper = rentalMapper;
        this.userRepository = userRepository;
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
                        () -> new NotFoundRentalException("Rental with id " + id + " not found")
                );
    }

    public void createRental(CreateRentalDto createRentalDto, String pictureUrl) {
        User user = userRepository.findById(3L)
                .orElseThrow(() -> new RuntimeException("Utilisateur fictif non trouvé"));

        Rental rental = rentalMapper.mapToEntity(createRentalDto, pictureUrl, user);

        rentalRepository.save(rental);
    }
}

package com.rental.rental.controllers;


import com.rental.rental.dto.request.CreateRentalDto;
import com.rental.rental.dto.RentalDto;
import com.rental.rental.dto.request.UpdateRentalDto;
import com.rental.rental.dto.response.RentalResponse;
import com.rental.rental.dto.response.MessageResponse;
import com.rental.rental.exceptions.MissingPictureException;
import com.rental.rental.services.CloudinaryService;
import com.rental.rental.services.JWTService;
import com.rental.rental.services.RentalService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/api/rentals")
public class RentalController {
    private final RentalService rentalService;

    private final CloudinaryService cloudinaryService;

    private final JWTService jwtService;

    public RentalController(RentalService rentalService, CloudinaryService cloudinaryService, JWTService jwtService) {
        this.rentalService = rentalService;
        this.cloudinaryService = cloudinaryService;
        this.jwtService = jwtService;
    }

    @GetMapping("")
    public RentalResponse getAllRentals() {
        return rentalService.getAllRentals();
    }

    @GetMapping("/{id}")
    public RentalDto getRentalById(@PathVariable("id") Long id) {
        return rentalService.getRentalById(id);
    }

    @PostMapping("")
    public ResponseEntity<MessageResponse> createRental(@RequestParam("picture") MultipartFile file,
                                                        @Valid @ModelAttribute CreateRentalDto rentalDto,
                                                        @RequestHeader("Authorization") String authorizationHeader) {
        if (file == null || file.isEmpty()) {
            throw new MissingPictureException("Picture file is mandatory and cannot be empty.");
        }
        try {
            String token = authorizationHeader.substring(7);
            Jwt decodedToken = jwtService.decodeToken(token);

            String pictureUrl = cloudinaryService.uploadFile(file);
            rentalService.createRental(rentalDto, pictureUrl, decodedToken);
            MessageResponse responseMessage = new MessageResponse("Rental created !");
            return ResponseEntity.ok(responseMessage);

        } catch (Exception e) {
            throw new RuntimeException("Failed to upload file: " + e.getMessage());
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<MessageResponse> updateRental(
            @PathVariable("id") Long id,
            @Valid @ModelAttribute UpdateRentalDto updateRentalDto) {

        rentalService.updateRental(id, updateRentalDto);
        return ResponseEntity.ok(new MessageResponse("Rental updated successfully !"));
    }
}

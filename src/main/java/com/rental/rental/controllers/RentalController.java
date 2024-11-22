package com.rental.rental.controllers;


import com.rental.rental.dto.request.CreateRentalDto;
import com.rental.rental.dto.RentalDto;
import com.rental.rental.dto.response.RentalResponse;
import com.rental.rental.dto.response.ResponseMessage;
import com.rental.rental.exceptions.MissingPictureException;
import com.rental.rental.services.CloudinaryService;
import com.rental.rental.services.RentalService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/api")
public class RentalController {
    private final RentalService rentalService;

    private final CloudinaryService cloudinaryService;

    public RentalController(RentalService rentalService, CloudinaryService cloudinaryService) {
        this.rentalService = rentalService;
        this.cloudinaryService = cloudinaryService;
    }

    @GetMapping("/rentals")
    public RentalResponse getAllRentals() {
        return rentalService.getAllRentals();
    }

    @GetMapping("/rentals/{id}")
    public RentalDto getRentalById(@PathVariable("id") Long id) {
        return rentalService.getRentalById(id);
    }

    @PostMapping("/rentals")
    public ResponseEntity<ResponseMessage> createRental(@RequestParam("picture") MultipartFile file,
                                                        @Valid @ModelAttribute CreateRentalDto rentalDto) {
        if (file == null || file.isEmpty()) {
            throw new MissingPictureException("Picture file is mandatory and cannot be empty.");
        }
        try {
            String pictureUrl = cloudinaryService.uploadFile(file);
            rentalService.createRental(rentalDto, pictureUrl);
            ResponseMessage responseMessage = new ResponseMessage("Rental created!");
            return ResponseEntity.ok(responseMessage);

        } catch (Exception e) {
            throw new RuntimeException("Failed to upload file: " + e.getMessage());
        }
    }
}

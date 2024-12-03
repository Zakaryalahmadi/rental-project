package com.rental.rental.services;


import com.rental.rental.dto.UserDto;
import com.rental.rental.dto.request.MessageRequestDto;
import com.rental.rental.entities.Message;
import com.rental.rental.entities.Rental;
import com.rental.rental.entities.User;
import com.rental.rental.repositories.MessageRepository;
import com.rental.rental.repositories.RentalRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

@Service
public class MessageService {
    private final MessageRepository messageRepository;
    private final RentalRepository rentalRepository;
    private final UserService userService;

    public MessageService(MessageRepository messageRepository, RentalRepository rentalRepository, UserService userService) {
        this.messageRepository = messageRepository;
        this.rentalRepository = rentalRepository;
        this.userService = userService;
    }

    public void createMessage(MessageRequestDto messageRequestDto, Jwt jwt) {
        UserDto currentUserDto = userService.getCurrentUser(jwt);

        User currentUser = userService.findByEmail(currentUserDto.email());

        Rental rental = rentalRepository.findById(messageRequestDto.rentalId())
                .orElseThrow(() -> new RuntimeException("Rental not found with ID: " + messageRequestDto.rentalId()));

        Message message = new Message();
        message.setUser(currentUser);
        message.setRental(rental);
        message.setMessage(messageRequestDto.message());

        messageRepository.save(message);
    }
}

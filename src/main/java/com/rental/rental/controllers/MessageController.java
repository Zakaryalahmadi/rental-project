package com.rental.rental.controllers;

import com.rental.rental.dto.request.MessageRequestDto;
import com.rental.rental.dto.response.MessageResponse;
import com.rental.rental.services.JWTService;
import com.rental.rental.services.MessageService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/messages")
@Tag(name = "Messages", description = "Endpoints for managing messages")
public class MessageController {

    private final MessageService messageService;
    private final JWTService jwtService;

    public MessageController(MessageService messageService, JWTService jwtService, JWTService jwtService1) {
        this.messageService = messageService;
        this.jwtService = jwtService1;
    }

    @PostMapping("")
    @Operation(summary = "Create a new message", description = "Creates a new message for a rental")
    public ResponseEntity<MessageResponse> createMessage(@RequestBody @Valid MessageRequestDto messageRequestDto, @RequestHeader("Authorization") String authorizationHeader) {
        String token = authorizationHeader.substring(7);
        Jwt jwt = jwtService.decodeToken(token);

        messageService.createMessage(messageRequestDto, jwt);
        return ResponseEntity.ok(new MessageResponse("Message send with success"));
    }
}

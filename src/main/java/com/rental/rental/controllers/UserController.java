package com.rental.rental.controllers;

import com.rental.rental.dto.UserDto;
import com.rental.rental.dto.request.LoginRequestDto;
import com.rental.rental.dto.request.RegisterRequestDto;
import com.rental.rental.dto.response.TokenResponse;
import com.rental.rental.entities.User;
import com.rental.rental.services.JWTService;
import com.rental.rental.services.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;


@RestController
@Slf4j
@RequestMapping("/api/")
@Tag(name = "Users", description = "Endpoints for user management and authentication")
public class UserController {

    private final JWTService jwtService;
    private final UserService userService;

    public UserController(JWTService jwtService, UserService userService, PasswordEncoder passwordEncoder) {
        this.jwtService = jwtService;
        this.userService = userService;
    }

    @GetMapping("auth/me")
    @Operation(summary = "Get the current user", description = "Retrieve the details of the currently authenticated user using the JWT token.")
    public ResponseEntity<UserDto> getCurrentUser(@RequestHeader("Authorization") String authorizationHeader) {

        String token = authorizationHeader.substring(7);

        var decodedJwt = jwtService.decodeToken(token);
        UserDto userDto = userService.getCurrentUser(decodedJwt);
        return ResponseEntity.ok(userDto);
    }

    @PostMapping("auth/login")
    @Operation(summary = "User login", description = "Authenticate a user by email and password, and return a JWT token.")
    public ResponseEntity<TokenResponse> login(@RequestBody @Valid LoginRequestDto loginRequest) {
        String token = userService.loginUser(loginRequest.email(), loginRequest.password());
        return ResponseEntity.ok(new TokenResponse(token));
    }

    @PostMapping("auth/register")
    @Operation(summary = "User registration", description = "Register a new user and return a JWT token.")
    public ResponseEntity<TokenResponse> register(@RequestBody @Valid RegisterRequestDto registerRequest) {
        userService.registerUser(registerRequest);

        User registeredUser = userService.findByEmail(registerRequest.email());

        String token = jwtService.generateToken(registeredUser);

        return ResponseEntity.ok(new TokenResponse(token));
    }

    @GetMapping("/user/{id}")
    @Operation(summary = "Get user by ID", description = "Retrieve the details of a user by their ID.")
    public UserDto getUserById(@PathVariable("id") Long id){
        return userService.getUserById(id);
    }

}
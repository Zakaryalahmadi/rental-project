package com.rental.rental.controllers;

import com.rental.rental.dto.UserDto;
import com.rental.rental.dto.request.LoginRequestDto;
import com.rental.rental.dto.request.RegisterRequestDto;
import com.rental.rental.dto.response.TokenResponse;
import com.rental.rental.entities.User;
import com.rental.rental.services.JWTService;
import com.rental.rental.services.UserService;
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
public class UserController {

    private final JWTService jwtService;
    private final UserService userService;
    private final PasswordEncoder passwordEncoder;

    public UserController(JWTService jwtService, UserService userService, PasswordEncoder passwordEncoder) {
        this.jwtService = jwtService;
        this.userService = userService;
        this.passwordEncoder = passwordEncoder;
    }

    @GetMapping("auth/me")
    public ResponseEntity<UserDto> getCurrentUser(@RequestHeader("Authorization") String authorizationHeader) {

        String token = authorizationHeader.substring(7);

        var decodedJwt = jwtService.decodeToken(token);
        UserDto userDto = userService.getCurrentUser(decodedJwt);
        return ResponseEntity.ok(userDto);
    }

    @PostMapping("auth/login")
    public ResponseEntity<TokenResponse> login(@RequestBody @Valid LoginRequestDto loginRequest) {
        User user = userService.findByEmail(loginRequest.email());

        if (!passwordEncoder.matches(loginRequest.password(), user.getPassword())) {// le faire dans un service
            throw new IllegalArgumentException("Invalid email or password");
        }

        String token = jwtService.generateToken(user);

        return ResponseEntity.ok(new TokenResponse(token));
    }

    @PostMapping("auth/register")
    public ResponseEntity<TokenResponse> register(@RequestBody @Valid RegisterRequestDto registerRequest) {
        userService.registerUser(registerRequest);

        User registeredUser = userService.findByEmail(registerRequest.email());

        String token = jwtService.generateToken(registeredUser);

        return ResponseEntity.ok(new TokenResponse(token));
    }

    @GetMapping("/user/{id}")
    public UserDto getUserById(@PathVariable("id") Long id){
        return userService.getUserById(id);
    }

}
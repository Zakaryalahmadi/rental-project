package com.rental.rental.services;

import com.rental.rental.dto.UserDto;
import com.rental.rental.dto.UserMapper;
import com.rental.rental.dto.request.RegisterRequestDto;
import com.rental.rental.entities.User;
import com.rental.rental.exceptions.UserNotFoundException;
import com.rental.rental.repositories.UserRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.provisioning.UserDetailsManager;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final UserMapper userMapper;

    public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder, UserMapper userMapper) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.userMapper = userMapper;
    }

    public User findByEmail(String email) {
        return userRepository.findByEmail(email)
                .orElseThrow(() -> new UserNotFoundException("User with email " + email + " not found"));
    }

    public void registerUser(RegisterRequestDto registerRequest) {
        userRepository.findByEmail(registerRequest.email()).ifPresent(user -> {
            throw new IllegalArgumentException("Email already exists");
        });

        User newUser = User.builder()
                .email(registerRequest.email())
                .name(registerRequest.name())
                .password(passwordEncoder.encode(registerRequest.password()))
                .build();

        userRepository.save(newUser);
    }

    public UserDto getCurrentUser(Jwt decodedJwt) {
        String email = decodedJwt.getSubject();
        User user = findByEmail(email);
        return userMapper.apply(user);
    }
}
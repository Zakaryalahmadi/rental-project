package com.rental.rental.services;

import com.rental.rental.dto.UserDto;
import com.rental.rental.mappers.UserMapper;
import com.rental.rental.dto.request.RegisterRequestDto;
import com.rental.rental.entities.User;
import com.rental.rental.exceptions.UserNotFoundException;
import com.rental.rental.repositories.UserRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final UserMapper userMapper;
    private final JWTService jwtService;

    public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder, UserMapper userMapper, JWTService jwtService) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.userMapper = userMapper;
        this.jwtService = jwtService;
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

    public UserDto getUserById(Long id){
        return userRepository.findById(id).map(userMapper).orElseThrow(() -> new UserNotFoundException("User with id " + id + " not found"));
    }

    public UserDto getCurrentUser(Jwt decodedJwt) {
        String email = decodedJwt.getSubject();
        User user = findByEmail(email);
        return userMapper.apply(user);
    }

    public String loginUser(String email, String password) {
        User user = findByEmail(email);

        if (!passwordEncoder.matches(password, user.getPassword())) {
            throw new IllegalArgumentException("Invalid email or password");
        }

        return jwtService.generateToken(user);
    }
}
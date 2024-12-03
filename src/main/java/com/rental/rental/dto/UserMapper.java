package com.rental.rental.dto;

import com.rental.rental.entities.User;
import org.springframework.stereotype.Component;

import java.util.function.Function;

@Component
public class UserMapper implements Function<User, UserDto> {

    @Override
    public UserDto apply(User user) {
        return new UserDto(
                user.getId(),
                user.getName(),
                user.getEmail(),
                user.getCreatedAt(),
                user.getUpdatedAt()
        );
    }
}

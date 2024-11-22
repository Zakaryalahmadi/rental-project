package com.rental.rental.exceptions;

import java.time.LocalDateTime;
import java.util.List;

public record ApiError(
        String path,
        List<String> message,
        int statusCode,
        LocalDateTime timestamp
) {}

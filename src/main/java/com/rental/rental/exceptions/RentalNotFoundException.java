package com.rental.rental.exceptions;

public class RentalNotFoundException extends RuntimeException {

    public RentalNotFoundException(String message) {
        super(message);
    }
}
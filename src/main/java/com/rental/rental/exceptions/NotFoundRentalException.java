package com.rental.rental.exceptions;

public class NotFoundRentalException extends RuntimeException {

    public NotFoundRentalException(String message) {
        super(message);
    }
}
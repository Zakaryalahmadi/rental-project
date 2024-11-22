package com.rental.rental.exceptions;

public class MissingPictureException extends RuntimeException {
    public MissingPictureException(String message) {
        super(message);
    }
}

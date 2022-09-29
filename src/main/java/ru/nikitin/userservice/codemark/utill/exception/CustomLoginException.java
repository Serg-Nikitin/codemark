package ru.nikitin.userservice.codemark.utill.exception;

public class CustomLoginException extends RuntimeException {
    public CustomLoginException(String message) {
        super(message);
    }
}

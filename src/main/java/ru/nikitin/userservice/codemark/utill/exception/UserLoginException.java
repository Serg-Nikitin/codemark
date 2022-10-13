package ru.nikitin.userservice.codemark.utill.exception;

public class UserLoginException extends RuntimeException {
    public UserLoginException(String message) {
        super(message);
    }
}

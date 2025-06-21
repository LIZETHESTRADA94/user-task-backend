package com.user_service.exception;

public class InvalidUserException extends BusinessException {

    public InvalidUserException(String reason) {
        super(reason);
    }
}

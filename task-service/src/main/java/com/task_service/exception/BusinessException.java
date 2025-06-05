package com.task_service.exception;

import org.apache.hc.core5.http.HttpStatus;

public class BusinessException extends RuntimeException {
    int httpStatusCode = HttpStatus.SC_BAD_REQUEST;

    public BusinessException(String message) {
        super(message);
    }

    public BusinessException(String message, int statusCode) {
        super(message);

        this.httpStatusCode = statusCode;
    }
}

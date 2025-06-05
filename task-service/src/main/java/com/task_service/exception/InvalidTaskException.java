package com.task_service.exception;

public class InvalidTaskException extends BusinessException {
    
    public InvalidTaskException(String reason) {
        super("Tarea inválida: " + reason);
    }
}

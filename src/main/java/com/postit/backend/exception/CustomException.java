package com.postit.backend.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public class CustomException extends RuntimeException{
    private static final long serialVersionUID = 1L;
    private HttpStatus status;
    public CustomException(String message, HttpStatus status)
    {
        super(message);
        this.status=status;
    }
}

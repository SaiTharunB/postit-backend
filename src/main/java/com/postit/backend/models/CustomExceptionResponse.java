package com.postit.backend.models;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.springframework.http.HttpStatus;

import java.time.LocalDateTime;
@Getter
@Setter
public class CustomExceptionResponse {
    private LocalDateTime timestamp=LocalDateTime.now();
    private Integer code;
    private String message;

    private String reason;

    public CustomExceptionResponse(String message, HttpStatus status)
    {
        this.message=message;
        this.code= status.value();
        this.reason=status.getReasonPhrase();
    }
}

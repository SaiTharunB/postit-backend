package com.postit.backend.exception;

import com.postit.backend.models.CustomExceptionResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import javax.validation.ConstraintViolationException;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.stream.Collectors;

@ControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(value = CustomException.class)
    public ResponseEntity<CustomExceptionResponse> exception(CustomException exception) {
        return new ResponseEntity<>(new CustomExceptionResponse(exception.getMessage(),exception.getStatus()),HttpStatus.BAD_REQUEST);
    }
    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity handleValidtionErrors(ConstraintViolationException ex) {
        return new ResponseEntity<>(new CustomExceptionResponse(ex.getMessage(),HttpStatus.BAD_REQUEST),HttpStatus.BAD_REQUEST);
    }
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity handleValidtionErrors(MethodArgumentNotValidException ex) {
        Map<String,String> errors= new LinkedHashMap<>();
        ex.getBindingResult().getAllErrors().forEach(error->{
            String fieldName = ((FieldError) error).getField();
            String errorMessage = error.getDefaultMessage();
            errors.put(fieldName, errorMessage);
        });
        String message = errors.entrySet().stream().map(e->e.getValue()).collect(Collectors.joining(", "));
        return new ResponseEntity<>(new CustomExceptionResponse(message,HttpStatus.BAD_REQUEST),HttpStatus.BAD_REQUEST);
    }
}

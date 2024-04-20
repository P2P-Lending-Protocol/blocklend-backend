package com.blocklend.lending.protocol.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public Map<String, String> handleInvalidArgumentFieldExceptions(MethodArgumentNotValidException exception) {
        Map<String, String> errorsMapped = new HashMap<>();
        exception.getBindingResult()
                .getFieldErrors()
                .forEach(error -> errorsMapped.put(error.getField(), error.getDefaultMessage()));
        return errorsMapped;
    }

    @ExceptionHandler(OfWeb3Exception.class)
    public ResponseEntity<Map<String, String>> handleGeegStarExceptions(OfWeb3Exception exception) {
        Map<String, String> error = new HashMap<>();
        error.put("Error ", exception.getMessage());
        return ResponseEntity.status(exception.getHttpStatus()).body(error);
    }

}


package com.example.passport_service.rest.exceptions;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.Map;
import java.util.stream.Collectors;

@RestControllerAdvice
public class RestrictionsExceptionHandler {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<?> handle(final MethodArgumentNotValidException e) {
        return ResponseEntity.badRequest().body(
            e.getBindingResult().getFieldErrors().stream()
                .map(f -> Map.of(
                        f.getField(), f.getRejectedValue(),
                        "Description error: ", f.getDefaultMessage()
                    )
                )
                .collect(Collectors.toList())
        );
    }
}

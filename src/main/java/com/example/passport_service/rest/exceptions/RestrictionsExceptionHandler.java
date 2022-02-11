package com.example.passport_service.rest.exceptions;

import com.example.passport_service.dto.ErrorResponseDto;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.Optional;
import java.util.stream.Collectors;

@RestControllerAdvice
public class RestrictionsExceptionHandler {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<?> handle(final MethodArgumentNotValidException e) {
        return ResponseEntity.badRequest().body(
            e.getBindingResult().getFieldErrors().stream()
                .map(f -> ErrorResponseDto.builder()
                    .field(f.getField())
                    .description(f.getDefaultMessage())
                    .givenValue(
                        Optional.ofNullable(f.getRejectedValue())
                            .map(String::valueOf)
                            .orElse("")
                    )
                    .build()
                )
                .collect(Collectors.toList())
        );
    }
}

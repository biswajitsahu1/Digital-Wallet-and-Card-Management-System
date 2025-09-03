package com.bank.user.user_service.exception;

import com.bank.user.user_service.dto.ErrorResponseDto;
import io.micrometer.tracing.Tracer;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

@Slf4j
@ControllerAdvice
public class GlobalExceptionHandler {

    @Autowired(required = false)
    private Tracer tracer;

    private String getTraceId() {
        if (tracer != null && tracer.currentSpan() != null) {
            return tracer.currentSpan().context().traceId();
        }
        return null;
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponseDto> handleGlobalException(Exception exception, WebRequest request) {
        log.error("Unhandled exception", exception);
        HttpStatus status = HttpStatus.INTERNAL_SERVER_ERROR;

        return ResponseEntity.status(status).body(
                new ErrorResponseDto(
                        request.getDescription(false),
                        status.name(),
                        exception.getMessage(),
                        LocalDateTime.now(),
                        getTraceId(),
                        null
                )
        );
    }

    @ExceptionHandler(InvalidCredentialsException.class)
    public ResponseEntity<ErrorResponseDto> handleInvalidCredentialsException(
            InvalidCredentialsException exception, WebRequest request) {
        log.error("Invalid credentials exception", exception);
        HttpStatus status = HttpStatus.UNAUTHORIZED;

        return ResponseEntity.status(status).body(
                new ErrorResponseDto(
                        request.getDescription(false),
                        status.name(),
                        exception.getMessage(),
                        LocalDateTime.now(),
                        getTraceId(),
                        null
                )
        );
    }

    @ExceptionHandler(UserAlreadyExistsException.class)
    public ResponseEntity<ErrorResponseDto> handleUserAlreadyExistsException(
            UserAlreadyExistsException exception, WebRequest request) {
        log.error("User already exists exception", exception);
        HttpStatus status = HttpStatus.CONFLICT;

        return ResponseEntity.status(status).body(
                new ErrorResponseDto(
                        request.getDescription(false),
                        status.name(),
                        exception.getMessage(),
                        LocalDateTime.now(),
                        getTraceId(),
                        null
                )
        );
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorResponseDto> handleValidationErrors(MethodArgumentNotValidException ex,
                                                                   WebRequest request) {
        log.error("Validation exception", ex);
        Map<String, String> errors = new HashMap<>();
        ex.getBindingResult().getFieldErrors().forEach(error ->
                errors.put(error.getField(), error.getDefaultMessage()));
        HttpStatus status = HttpStatus.BAD_REQUEST;

        return ResponseEntity.status(status).body(
                new ErrorResponseDto(
                        request.getDescription(false),
                        status.name(),
                        "Validation failed",
                        LocalDateTime.now(),
                        getTraceId(),
                        errors
                )
        );
    }

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<ErrorResponseDto> handleIllegalArgumentException(IllegalArgumentException exception, WebRequest request) {
        log.error("Illegal argument exception", exception);
        HttpStatus status = HttpStatus.NOT_FOUND;

        return ResponseEntity.status(status).body(
                new ErrorResponseDto(
                        request.getDescription(false),
                        status.name(),
                        exception.getMessage(),
                        LocalDateTime.now(),
                        getTraceId(),
                        null
                )
        );
    }
}
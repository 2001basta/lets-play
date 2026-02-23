package com.example.play.exception;

import java.nio.file.AccessDeniedException;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.resource.NoResourceFoundException;

import com.example.play.dto.Response;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(AccessDeniedException.class)
    public ResponseEntity<?> handleAccessDenied(AccessDeniedException ex) {
        return buildResponse(HttpStatus.FORBIDDEN, ex.getMessage());
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<?> handleValidation(MethodArgumentNotValidException ex) {
        String message = ex.getBindingResult()
                .getFieldError()
                .getDefaultMessage();

        return buildResponse(HttpStatus.BAD_REQUEST, message);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<?> handleGeneral(Exception ex) {
        return buildResponse(HttpStatus.INTERNAL_SERVER_ERROR,
                "Something went wrong");
    }

    @ExceptionHandler(HttpRequestMethodNotSupportedException.class)
    public ResponseEntity<Response<?>> handleMethodNotSupported(
            HttpRequestMethodNotSupportedException ex) {

        String message = "Method " + ex.getMethod() +
                " Supported methods: " +
                ex.getSupportedHttpMethods();

        return buildResponse(HttpStatus.METHOD_NOT_ALLOWED, message);
    }

    @ExceptionHandler(NoResourceFoundException.class)
    public ResponseEntity<Response<?>> handleResourceNotFound(
            NoResourceFoundException ex) {

        String message = "Path Not Found";
        return buildResponse(HttpStatus.METHOD_NOT_ALLOWED, message);
    }
    @ExceptionHandler(BadCredentialsException.class)
    public ResponseEntity<Response<?>> handleResourceNotFound(
            BadCredentialsException ex) {

        String message = "Invalid email or password";
        return buildResponse(HttpStatus.UNAUTHORIZED, message);
    }

    private ResponseEntity<Response<?>> buildResponse(HttpStatus status, String message) {
        return ResponseEntity.status(status)
                .body(Response.error(null, message));
    }
}

package com.example.demo.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.server.MethodNotAllowedException;
import org.webjars.NotFoundException;

import java.util.Date;

@ControllerAdvice
public class ControllerExceptionHandler {
    @ExceptionHandler(NotFoundException.class)
    public ResponseEntity<ErrorMessage> resourceNotFoundException(NotFoundException ex) {
        ErrorMessage message = new ErrorMessage(
                HttpStatus.NOT_FOUND.value(),
                new Date(),
                ex.getMessage(),
                "Can't found resource"
        );
        return new ResponseEntity<>(message, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<ErrorMessage> duplicatedResource(IllegalArgumentException ex) {
        ErrorMessage message = new ErrorMessage(
                HttpStatus.CONFLICT.value(),
                new Date(),
                ex.getMessage(),
                "Conflict resource"
        );
        return new ResponseEntity<>(message, HttpStatus.CONFLICT);
    }


    @ExceptionHandler(AuthenticationException.class)
    @ResponseBody
    public ResponseEntity<ErrorMessage> handleAuthenticationException(AuthenticationException ex) {
        ErrorMessage re = new ErrorMessage(
                HttpStatus.UNAUTHORIZED.value(),
                new Date(),
                ex.getMessage(),
                "Authentication failed. Please try again!"
        );
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(re);
    }


    @ExceptionHandler(MethodNotAllowedException.class)
    public ResponseEntity<ErrorMessage> notPermission(MethodNotAllowedException ex) {
        ErrorMessage message = new ErrorMessage(
                HttpStatus.METHOD_NOT_ALLOWED.value(),
                new Date(),
                ex.getMessage(),
                "Don't have permission"
        );
        return new ResponseEntity<>(message, HttpStatus.METHOD_NOT_ALLOWED);
    }

}

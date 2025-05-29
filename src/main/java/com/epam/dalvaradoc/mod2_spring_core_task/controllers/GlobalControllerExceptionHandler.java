/* (C)2025 */
package com.epam.dalvaradoc.mod2_spring_core_task.controllers;

import com.epam.dalvaradoc.mod2_spring_core_task.errors.BadCredentialsException;
import jakarta.validation.ConstraintViolationException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@Slf4j
@RestControllerAdvice
public class GlobalControllerExceptionHandler extends ResponseEntityExceptionHandler {

    @ResponseStatus(value = HttpStatus.UNAUTHORIZED) // 401
    @ExceptionHandler(BadCredentialsException.class)
    public ResponseEntity<String> badAuthentication() {
        LOGGER.error("Bad credentials");
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Bad credentials");
    }

    @ResponseStatus(value = HttpStatus.BAD_REQUEST)
    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<String> handleConstraintViolationException(Exception e) {
        LOGGER.error("The validation of a parameter failed: " + e.getMessage());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body("The validation of a parameter failed: " + e.getMessage());
    }

    @ResponseStatus(value = HttpStatus.BAD_REQUEST) // 401
    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<String> badArguments() {
        LOGGER.error("Bad arguments");
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Bad arguments");
    }
}

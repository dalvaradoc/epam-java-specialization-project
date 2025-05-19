package com.epam.dalvaradoc.mod2_spring_core_task.controllers;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;

import com.epam.dalvaradoc.mod2_spring_core_task.errors.BadCredentialsException;

import jakarta.validation.ConstraintViolationException;
import lombok.extern.slf4j.Slf4j;

@ControllerAdvice
@Slf4j
public class GlobalControllerExceptionHandler {

  @ResponseStatus(value=HttpStatus.UNAUTHORIZED)  // 401
  @ExceptionHandler(BadCredentialsException.class)
  public void badAuthentication() {
  }

  @ResponseStatus(value = HttpStatus.BAD_REQUEST) 
  @ExceptionHandler(ConstraintViolationException.class)
  public void handleConstraintViolationException(Exception e){
    LOGGER.error("The validation of a parameter failed: " + e.getMessage());
  }
}


package com.epam.dalvaradoc.mod2_spring_core_task.errors;

public class BadCredentialsException extends RuntimeException {
    public BadCredentialsException (String errorMessage, Throwable err) {
        super(errorMessage, err);
    }
    public BadCredentialsException (String errorMessage) {
        super(errorMessage);
    }
}

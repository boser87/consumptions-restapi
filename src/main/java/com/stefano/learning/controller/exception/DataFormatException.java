package com.stefano.learning.controller.exception;

import org.springframework.validation.FieldError;

import java.util.List;

/**
 * for HTTP 400 errors
 */
public final class DataFormatException extends RuntimeException {

    private List<FieldError> fieldErrors;

    public DataFormatException(String message, List<FieldError> fieldErrors) {
        super(message);
        this.fieldErrors = fieldErrors;
    }

    public List<FieldError> getFieldErrors() {
        return fieldErrors;
    }
}
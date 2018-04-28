package com.stefano.learning.controller.exception;

public class ConsumptionsApplicationException extends Exception {
    private String error;

    public ConsumptionsApplicationException(String error) {
        super();
        this.error = error;
    }

    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }
}

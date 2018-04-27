package com.stefano.learning.utils.filereader;

public class ConsumptionsFileReadingException extends RuntimeException {

    private String error;

    ConsumptionsFileReadingException(String error) {
        this.error = error;
    }

    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }
}

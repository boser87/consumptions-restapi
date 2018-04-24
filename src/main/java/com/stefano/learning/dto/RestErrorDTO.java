package com.stefano.learning.dto;

public class RestErrorDTO {

    public final String detail;
    public final String message;

    public RestErrorDTO(Exception ex, String detail) {
        this.message = ex.getLocalizedMessage();
        this.detail = detail;
    }
}

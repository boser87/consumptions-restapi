package com.stefano.learning.core.dto;

import com.stefano.learning.core.controller.exception.ConsumptionsApplicationException;

public class RestErrorDTO {
    private final String message;

    public RestErrorDTO(ConsumptionsApplicationException ex) {
        this.message = ex.getError();
    }

    public String getMessage() {
        return message;
    }
}

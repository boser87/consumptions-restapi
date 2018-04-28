package com.stefano.learning.dto;

import com.stefano.learning.controller.exception.ConsumptionsApplicationException;

public class RestErrorDTO {
    public final String message;

    public RestErrorDTO(ConsumptionsApplicationException ex) {
        this.message = ex.getError();
    }
}

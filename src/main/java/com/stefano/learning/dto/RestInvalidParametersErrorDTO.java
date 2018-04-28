package com.stefano.learning.dto;

import com.stefano.learning.controller.exception.ConsumptionsApplicationException;

import java.util.List;

public class RestInvalidParametersErrorDTO extends RestErrorDTO {

    public final List<String> fieldsWithError;

    public RestInvalidParametersErrorDTO(ConsumptionsApplicationException ex, List<String> fieldsWithError) {
        super(ex);
        this.fieldsWithError = fieldsWithError;
    }
}

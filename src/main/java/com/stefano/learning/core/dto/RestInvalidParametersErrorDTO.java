package com.stefano.learning.core.dto;

import com.stefano.learning.core.controller.exception.ConsumptionsApplicationException;

import java.util.List;

public class RestInvalidParametersErrorDTO extends RestErrorDTO {

    private final List<String> fieldsWithError;

    public RestInvalidParametersErrorDTO(ConsumptionsApplicationException ex, List<String> fieldsWithError) {
        super(ex);
        this.fieldsWithError = fieldsWithError;
    }

    public List<String> getFieldsWithError() {
        return fieldsWithError;
    }
}

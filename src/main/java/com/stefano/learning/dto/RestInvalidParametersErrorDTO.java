package com.stefano.learning.dto;

import java.util.List;

public class RestInvalidParametersErrorDTO extends RestErrorDTO {

    public final List<String> fieldsWithError;

    public RestInvalidParametersErrorDTO(Exception ex, String detail, List<String> fieldsWithError) {
        super(ex, detail);
        this.fieldsWithError = fieldsWithError;
    }
}

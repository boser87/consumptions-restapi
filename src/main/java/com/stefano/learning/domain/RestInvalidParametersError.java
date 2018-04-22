package com.stefano.learning.domain;

import java.util.List;

public class RestInvalidParametersError extends RestErrorInfo {

    public final List<String> fieldsWithError;

    public RestInvalidParametersError(Exception ex, String detail, List<String> fieldsWithError) {
        super(ex, detail);
        this.fieldsWithError = fieldsWithError;
    }
}

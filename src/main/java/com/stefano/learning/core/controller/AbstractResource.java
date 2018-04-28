package com.stefano.learning.core.controller;

import com.fasterxml.jackson.databind.exc.InvalidFormatException;
import com.stefano.learning.core.controller.exception.ConsumptionNotFoundException;
import com.stefano.learning.core.controller.exception.DataFormatException;
import com.stefano.learning.core.controller.exception.ConsumptionsApplicationException;
import com.stefano.learning.core.dto.RestErrorDTO;
import com.stefano.learning.core.dto.RestInvalidParametersErrorDTO;
import com.stefano.learning.consumptions.utils.filereader.ConsumptionsFileReadingException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.context.request.WebRequest;

import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public abstract class AbstractResource {

    private final Logger log = LoggerFactory.getLogger(this.getClass());

    @ExceptionHandler({HttpMessageNotReadableException.class, InvalidFormatException.class})
    @ResponseStatus(value = HttpStatus.BAD_REQUEST)
    public RestInvalidParametersErrorDTO handleInputNotReadableException(Exception exception) {
        List<String> invalidFieldsList = new ArrayList<>();
        final Throwable cause = exception.getCause() == null ? exception : exception.getCause();
        if (cause instanceof InvalidFormatException) {
            for (InvalidFormatException.Reference reference : ((InvalidFormatException) cause)
                    .getPath()) {
                invalidFieldsList.add(reference.getFieldName());
            }
        }
        return new RestInvalidParametersErrorDTO(new ConsumptionsApplicationException("JSON is malformed!"), invalidFieldsList);
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler({DataFormatException.class})
    public RestInvalidParametersErrorDTO handleDataStoreException(DataFormatException ex, WebRequest request, HttpServletResponse response) {
        log.info("DataFormatException to RestResponse: " + ex.getMessage());

        List<String> invalidFieldsList = ex.getFieldErrors().stream().map(FieldError::getField).collect(Collectors.toList());

        return new RestInvalidParametersErrorDTO(new ConsumptionsApplicationException("Following fields are missing or have invalid format!"), invalidFieldsList);
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler({ConsumptionsFileReadingException.class})
    public RestErrorDTO handleConsumptionReaderException(ConsumptionsFileReadingException ex, WebRequest request, HttpServletResponse response) {
        log.info("ConsumptionsFileReadingException to RestResponse: " + ex.getMessage());

        List<String> invalidFieldsList = new ArrayList<>();
        invalidFieldsList.add("file");

        return new RestErrorDTO(ex);
    }

    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler({ConsumptionNotFoundException.class})
    public RestErrorDTO handleConsumptionReaderException(ConsumptionNotFoundException ex, WebRequest request, HttpServletResponse response) {
        log.info("handleConsumptionReaderException to RestResponse: " + ex.getMessage());

        return new RestErrorDTO(ex);
    }
}

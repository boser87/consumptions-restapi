package com.stefano.learning.controller;

import java.net.URI;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.exc.InvalidFormatException;
import com.fasterxml.jackson.databind.exc.InvalidTypeIdException;
import com.fasterxml.jackson.databind.exc.UnrecognizedPropertyException;
import com.stefano.learning.controller.exception.DataFormatException;
import com.stefano.learning.domain.Consumption;
import com.stefano.learning.domain.RestErrorInfo;
import com.stefano.learning.domain.RestInvalidParametersError;
import com.stefano.learning.dto.ConsumptionByMonthDTO;
import com.stefano.learning.service.ConsumptionService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

@RestController
@RequestMapping("consumptions")
public class ConsumptionResource {

    protected final Logger log = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private ConsumptionService consumptionService;

    @PostMapping
    public ResponseEntity<Object> createConsumption(@Valid @RequestBody Consumption consumption, BindingResult bindingResult) {

        if (bindingResult.hasErrors()) {
            throw new DataFormatException("Create consumption request contains invalid data", bindingResult.getFieldErrors());
        }

        Consumption savedConsumption = consumptionService.save(consumption);

        URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}")
                .buildAndExpand(savedConsumption.getId()).toUri();

        return ResponseEntity.created(location).build();

    }

    @GetMapping(params = "month")
    public List<ConsumptionByMonthDTO> getConsumptionsByMonth(@RequestParam("month") int month) {
        return consumptionService.getConsumptionsByMonth(month);
    }

    @GetMapping(params = {"month", "driverId"})
    public List<ConsumptionByMonthDTO> getConsumptionsByMonthAndByDriverId(@RequestParam("month") int month, @RequestParam("driverId") String driverId) {
        // TODO: return 404 when nothing is found
        return consumptionService.getConsumptionsByMonth(driverId, month);
    }

    // TODO: move exception handlers to another class
    @ExceptionHandler({HttpMessageNotReadableException.class, InvalidFormatException.class})
    @ResponseStatus(value = HttpStatus.BAD_REQUEST)
    public RestInvalidParametersError handleInputNotReadableException(Exception exception) {
        List<String> invalidFieldsList = new ArrayList<>();
        final Throwable cause = exception.getCause() == null ? exception : exception.getCause();
        if (cause instanceof InvalidFormatException) {
            for (InvalidFormatException.Reference reference : ((InvalidFormatException) cause)
                    .getPath()) {
                invalidFieldsList.add(reference.getFieldName());
            }
        }
        return new RestInvalidParametersError(exception, "Following fields have invalid format!", invalidFieldsList);
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler({DataFormatException.class})
    public
    @ResponseBody
    RestInvalidParametersError handleDataStoreException(DataFormatException ex, WebRequest request, HttpServletResponse response) {
        log.info("DataFormatException to RestResponse : " + ex.getMessage());

        String invalidFields = ex.getFieldErrors().stream().map(FieldError::getField).collect(Collectors.joining(","));
        List<String> invalidFieldsList = ex.getFieldErrors().stream().map(FieldError::getField).collect(Collectors.toList());

        return new RestInvalidParametersError(ex, "Following fields are missing or have invalid format!", invalidFieldsList);
    }
}

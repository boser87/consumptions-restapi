package com.stefano.learning.dto;

import com.stefano.learning.domain.Consumption;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.ArrayList;
import java.util.List;

public class ConsumptionsBatchDTO {

    private List<URI> consumptionsURI = new ArrayList<>();

    public ConsumptionsBatchDTO(List<Consumption> consumptions, String consumptionsResourceName) {
        consumptions.forEach(consumption -> consumptionsURI.add(ServletUriComponentsBuilder.fromCurrentContextPath().path(consumptionsResourceName).path("/{id}")
                .buildAndExpand(consumption.getId()).toUri()));
    }

    public List<URI> getConsumptionsURI() {
        return consumptionsURI;
    }
}

package com.stefano.learning.consumptions.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;

public class ConsumptionBatchDTO {

    @JsonIgnore
    private Long id;

    private URI consumptionURI;

    private String resourceName = "consumptions";

    public void setResourceName(String resourceName) {
        this.resourceName = resourceName;
    }

    public URI getConsumptionURI() {
        return ServletUriComponentsBuilder.fromCurrentContextPath().path(resourceName).path("/{id}")
                .buildAndExpand(id).toUri();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
}

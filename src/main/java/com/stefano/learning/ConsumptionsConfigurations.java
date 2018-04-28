package com.stefano.learning;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

@Component
@PropertySource("classpath:consumptions.properties")
public class ConsumptionsConfigurations {

    @Value("${batchSize}")
    private int batchSize;

    @Value("${resource.consumption.name}")
    private String consumptionsResourceName;

    public int getBatchSize() {
        return batchSize;
    }

    public void setBatchSize(int batchSize) {
        this.batchSize = batchSize;
    }

    public void setConsumptionsResourceName(String consumptionsResourceName) {
        this.consumptionsResourceName = consumptionsResourceName;
    }

    public String getConsumptionsResourceName() {
        return consumptionsResourceName;
    }
}

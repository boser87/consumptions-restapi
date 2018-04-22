package com.stefano.learning.dto;

import java.math.BigDecimal;

public class ConsumptionByFuelTypeDTO {
    private String fuelType;
    private BigDecimal totalVolume;
    private BigDecimal averagePrice;
    private BigDecimal totalPrice;

    public ConsumptionByFuelTypeDTO(String fuelType, BigDecimal totalVolume, BigDecimal averagePrice, BigDecimal totalPrice) {
        this.fuelType = fuelType;
        this.totalVolume = totalVolume;
        this.averagePrice = averagePrice;
        this.totalPrice = totalPrice;
    }

    public String getFuelType() {
        return fuelType;
    }

    public void setFuelType(String fuelType) {
        this.fuelType = fuelType;
    }

    public BigDecimal getTotalVolume() {
        return totalVolume;
    }

    public void setTotalVolume(BigDecimal totalVolume) {
        this.totalVolume = totalVolume;
    }

    public BigDecimal getAveragePrice() {
        return averagePrice;
    }

    public void setAveragePrice(BigDecimal averagePrice) {
        this.averagePrice = averagePrice;
    }

    public BigDecimal getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(BigDecimal totalPrice) {
        this.totalPrice = totalPrice;
    }
}

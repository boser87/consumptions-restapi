package com.stefano.learning.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.stefano.learning.domain.FuelType;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Calendar;
public class ConsumptionByMonthDTO {

    private String fuelType;
    private BigDecimal volume;
    private LocalDate date;
    private BigDecimal price;
    private BigDecimal totalPrice;
    private String driverId;

    public ConsumptionByMonthDTO(String fuelType, BigDecimal volume, LocalDate date, BigDecimal price, String driverId) {
        this.fuelType = fuelType;
        this.volume = volume;
        this.date = date;
        this.price = price;
        this.totalPrice = volume.multiply(price);
        this.driverId = driverId;
    }

    public String getFuelType() {
        return fuelType;
    }

    public void setFuelType(String fuelType) {
        this.fuelType = fuelType;
    }

    public BigDecimal getVolume() {
        return volume;
    }

    public void setVolume(BigDecimal volume) {
        this.volume = volume;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public BigDecimal getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(BigDecimal totalPrice) {
        this.totalPrice = totalPrice;
    }

    public String getDriverId() {
        return driverId;
    }

    public void setDriverId(String driverId) {
        this.driverId = driverId;
    }
}

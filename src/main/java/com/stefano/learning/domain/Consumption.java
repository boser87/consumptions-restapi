package com.stefano.learning.domain;

import com.stefano.learning.validation.ValidFuelType;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Calendar;

@Entity
@Access(AccessType.FIELD)
public class Consumption {

    @Id
    @GeneratedValue
    private Long id;
    @ValidFuelType
    private String fuelType;
    // TODO: is BigDecimal a good choice?
    @NotNull
    @Positive
    private BigDecimal price;
    @NotNull
    @Positive
    private BigDecimal volume;
    private LocalDate date;
    @NotEmpty
    private String driverId;

    // TODO: do I really need this constructor?
    public Consumption() {

    }

    // TODO: do I really need this constructor?
    public Consumption(Long id, String fuelType, BigDecimal price, BigDecimal volume, LocalDate date, String driverId) {
        super();
        this.id = id;
        this.fuelType = fuelType;
        this.price = price;
        this.volume = volume;
        this.date = date;
        this.driverId = driverId;
    }

    // TODO: can I avoid to have all getters and setters?
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getFuelType() {
        return fuelType;
    }

    public FuelType getFuelTypeAsEnum() {
        return FuelType.valueOf(fuelType);
    }

    public void setFuelType(String fuelType) {
        this.fuelType = fuelType;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
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

    @Access(AccessType.PROPERTY)
    @Column(name = "DATE_MONTH")
    public int getDateMonthAsInt() {
        return date.getMonth().getValue();
    }

    public void setDateMonthAsInt(int monthAsInt) {
        this.date = this.date.withMonth(monthAsInt);
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public String getDriverId() {
        return driverId;
    }

    public void setDriverId(String driverId) {
        this.driverId = driverId;
    }
}

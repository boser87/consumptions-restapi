package com.stefano.learning.domain;

public enum FuelType {
    _95("95"), _98("98"), D("D");

    private final String value;

    FuelType(final String value) {
        this.value = value;
    }

    @Override
    public String toString() {
        return value;
    }
}

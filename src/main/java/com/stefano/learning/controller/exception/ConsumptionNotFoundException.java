package com.stefano.learning.controller.exception;

public class ConsumptionNotFoundException extends ConsumptionsApplicationException {
    public ConsumptionNotFoundException(String error) {
        super(error);
    }
}

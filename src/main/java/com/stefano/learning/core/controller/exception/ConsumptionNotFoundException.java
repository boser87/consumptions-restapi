package com.stefano.learning.core.controller.exception;

public class ConsumptionNotFoundException extends ConsumptionsApplicationException {
    public ConsumptionNotFoundException(String error) {
        super(error);
    }
}

package com.stefano.learning.consumptions.utils.filereader;

import com.stefano.learning.core.controller.exception.ConsumptionsApplicationException;

public class ConsumptionsFileReadingException extends ConsumptionsApplicationException {

    ConsumptionsFileReadingException(String error) {
        super(error);
    }
}

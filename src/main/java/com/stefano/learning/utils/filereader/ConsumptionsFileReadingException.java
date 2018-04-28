package com.stefano.learning.utils.filereader;

import com.stefano.learning.controller.exception.ConsumptionsApplicationException;

public class ConsumptionsFileReadingException extends ConsumptionsApplicationException {

    ConsumptionsFileReadingException(String error) {
        super(error);
    }
}

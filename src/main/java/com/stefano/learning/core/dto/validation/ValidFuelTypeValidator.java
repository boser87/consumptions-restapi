package com.stefano.learning.core.dto.validation;

import com.stefano.learning.core.domain.FuelType;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.ArrayList;
import java.util.List;

public class ValidFuelTypeValidator implements ConstraintValidator<ValidFuelType, String> {

    private List<String> valueList = null;

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {

        if(value == null || value.isEmpty()) {
            return false;
        }

        return valueList.contains(value.toUpperCase());
    }

    @Override
    public void initialize(ValidFuelType validFuelType) {
        valueList = new ArrayList<String>();

        @SuppressWarnings("rawtypes")
        Enum[] enumValArr = FuelType.class.getEnumConstants();

        for(@SuppressWarnings("rawtypes")
                Enum enumVal : enumValArr) {
            valueList.add(enumVal.toString().toUpperCase());
        }

    }

}

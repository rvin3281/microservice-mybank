package com.arvend.accounts.customvalidation;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class SixteenDigitsValidator implements ConstraintValidator<SixteenDigits, Long> {

    @Override
    public void initialize(SixteenDigits constraintAnnotation) {
    }

    @Override
    public boolean isValid(Long value, ConstraintValidatorContext context) {
        if (value == null) {
            return true; // Use @NotNull to handle null cases
        }
        String valueAsString = value.toString();
        return valueAsString.length() == 16;
    }

}

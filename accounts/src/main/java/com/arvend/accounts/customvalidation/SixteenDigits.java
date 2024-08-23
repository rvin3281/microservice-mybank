package com.arvend.accounts.customvalidation;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Constraint(validatedBy = SixteenDigitsValidator.class)
@Target({ElementType.FIELD, ElementType.PARAMETER})
@Retention(RetentionPolicy.RUNTIME)
public @interface SixteenDigits {

    String message() default "The number must be exactly 16 digits long";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}

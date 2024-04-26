package com.ykotsiuba.clear_solution_test.configuration.validation;

import com.ykotsiuba.clear_solution_test.configuration.validation.annotations.ValidBirthDate;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

import java.time.LocalDate;

public class BirthDateValidator implements ConstraintValidator<ValidBirthDate, LocalDate> {
    @Override
    public boolean isValid(LocalDate value, ConstraintValidatorContext context) {
        if (value == null) {
            return true;
        }
        return value.isBefore(LocalDate.now());
    }
}

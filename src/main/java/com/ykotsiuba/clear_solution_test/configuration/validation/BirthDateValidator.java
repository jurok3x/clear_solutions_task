package com.ykotsiuba.clear_solution_test.configuration.validation;

import com.ykotsiuba.clear_solution_test.configuration.validation.annotations.ValidBirthDate;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import org.springframework.beans.factory.annotation.Value;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

public class BirthDateValidator implements ConstraintValidator<ValidBirthDate, LocalDate> {

    @Value("${minimum.age}")
    private Integer minimumAge;

    @Override
    public boolean isValid(LocalDate value, ConstraintValidatorContext context) {
        if (value == null) {
            return true;
        }
        LocalDate minimumDate = LocalDate.now().minus(minimumAge, ChronoUnit.YEARS);
        return value.isBefore(minimumDate);
    }
}

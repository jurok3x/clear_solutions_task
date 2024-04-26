package com.ykotsiuba.clear_solution_test.configuration.validation.annotations;

import com.ykotsiuba.clear_solution_test.configuration.validation.UserPatchValidator;
import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({ElementType.TYPE, ElementType.ANNOTATION_TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = UserPatchValidator.class)
public @interface ValidPatch {
    String message() default "at least one parameter should be present";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}

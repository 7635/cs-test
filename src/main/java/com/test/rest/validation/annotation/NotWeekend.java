package com.test.rest.validation.annotation;

import com.test.rest.validation.validators.NotWeekendValidator;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.*;

/**
 * Validates if annotated LocalDate field occurs during weekend
 */
@Documented
@Constraint(validatedBy = NotWeekendValidator.class)
@Target({ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
public @interface NotWeekend {
    String message() default "this is weekend";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
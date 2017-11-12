package com.test.rest.validation.annotation;

import com.test.rest.validation.validators.DateAfterDateValidator;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.ANNOTATION_TYPE;
import static java.lang.annotation.ElementType.TYPE;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

/**
 * Validates if precedingDate() is after succeedingDate()
 */
@Target({TYPE, ANNOTATION_TYPE})
@Retention(RUNTIME)
@Constraint(validatedBy = DateAfterDateValidator.class)
@Documented
public @interface DateAfterDate {
    String precedingDate();

    String succeedingDate();

    String message();

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}

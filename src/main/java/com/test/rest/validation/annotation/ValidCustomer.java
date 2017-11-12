package com.test.rest.validation.annotation;

import com.test.rest.validation.validators.CustomerValidator;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.*;

/**
 * Validates annotated field of type String as a valid customer
 */
@Documented
@Constraint(validatedBy = CustomerValidator.class)
@Target({ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
public @interface ValidCustomer {
    String message() default "invalid customer name";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}

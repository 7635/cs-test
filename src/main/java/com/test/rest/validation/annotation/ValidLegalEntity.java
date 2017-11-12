package com.test.rest.validation.annotation;

import com.test.rest.validation.validators.LegalEntityValidator;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.*;

/**
 * Checks if legal entity String field is valid
 */
@Documented
@Constraint(validatedBy = LegalEntityValidator.class)
@Target({ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
public @interface ValidLegalEntity {
    String message() default "invalid legal entity";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}

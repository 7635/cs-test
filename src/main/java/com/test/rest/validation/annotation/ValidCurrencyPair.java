package com.test.rest.validation.annotation;

import com.test.rest.validation.validators.CurrencyPairValidator;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.*;

/**
 * Validates if currency codes in currency pair (String) are valid ISO-4217 codes
 */
@Documented
@Constraint(validatedBy = CurrencyPairValidator.class)
@Target({ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
public @interface ValidCurrencyPair {
    String message() default "invalid ISO-4217 currency code in pair";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}

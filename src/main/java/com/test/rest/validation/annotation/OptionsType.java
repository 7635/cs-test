package com.test.rest.validation.annotation;

import com.test.rest.validation.validators.OptionsTypeValidator;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.ANNOTATION_TYPE;
import static java.lang.annotation.ElementType.TYPE;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

/**
 * Validates OPTIONS type specific constraints
 */
@Target({TYPE, ANNOTATION_TYPE})
@Retention(RUNTIME)
@Constraint(validatedBy = OptionsTypeValidator.class)
@Documented
public @interface OptionsType {
    enum Validation {
        DELIVERY_DATE, STYLE, EXCERCISE_START_DATE, CURRENCY
    }

    String message();
    Validation constraint();

    String type() default "type";
    String style() default "style";
    String tradeDate() default "tradeDate";
    String excerciseStartDate() default "excerciseStartDate";
    String expiryDate() default "expiryDate";
    String premiumDate() default "premiumDate";
    String deliveryDate() default "deliveryDate";
    String currency() default "";

    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};

    @Target({TYPE, ANNOTATION_TYPE})
    @Retention(RUNTIME)

    @interface Constraints {
        OptionsType[] value();
    }
}

package com.test.rest.validation.validators;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.lang.annotation.Annotation;

public abstract class BaseValidator<A extends Annotation,V> implements ConstraintValidator<A, V> {
    @Override
    public void initialize(A constraintAnnotation) {
    }

    @Override
    public abstract boolean isValid(V value, ConstraintValidatorContext context);
}
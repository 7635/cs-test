package com.test.rest.validation.validators;

import com.test.rest.validation.ValidationConstraints;
import com.test.rest.validation.annotation.ValidCustomer;

import javax.validation.ConstraintValidatorContext;
import java.util.Objects;

public class CustomerValidator extends BaseValidator<ValidCustomer, String> {
    private ValidationConstraints validationConstraints;

    public CustomerValidator(ValidationConstraints validationConstraints) {
        this.validationConstraints = Objects.requireNonNull(validationConstraints);
    }

    @Override
    public boolean isValid(String customer, ConstraintValidatorContext context) {
        return validationConstraints.isValidCustomer(customer);
    }
}

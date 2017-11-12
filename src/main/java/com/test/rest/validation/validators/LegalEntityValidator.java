package com.test.rest.validation.validators;

import com.test.rest.validation.ValidationConstraints;
import com.test.rest.validation.annotation.ValidLegalEntity;

import javax.validation.ConstraintValidatorContext;
import java.util.Objects;

public class LegalEntityValidator extends BaseValidator<ValidLegalEntity, String> {

    private ValidationConstraints validationConstraints;

    public LegalEntityValidator(ValidationConstraints validationConstraints) {
        this.validationConstraints = Objects.requireNonNull(validationConstraints);
    }

    @Override
    public boolean isValid(String candidate, ConstraintValidatorContext context) {
        return validationConstraints.isLegalEntity(candidate);
    }
}

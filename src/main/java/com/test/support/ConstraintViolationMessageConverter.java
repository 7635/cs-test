package com.test.support;

import com.test.rest.resource.ValidationErrorsResponse.ErrorField;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import javax.validation.ConstraintViolation;

@Component
public class ConstraintViolationMessageConverter<T>
        implements Converter<ConstraintViolation<T>, ErrorField> {

    @Override
    public ErrorField convert(ConstraintViolation<T> violation) {
        String [] fields = violation.getPropertyPath().toString().split("\\.");  // Path has no API..
        String field = fields.length > 2 ? fields[2] : "multiple fields relation";

        return new ErrorField(field, violation.getMessage());
    }
}

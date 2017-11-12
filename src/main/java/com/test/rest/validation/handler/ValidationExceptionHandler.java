package com.test.rest.validation.handler;

import com.test.rest.resource.ValidationErrorsResponse;
import com.test.rest.resource.ValidationErrorsResponse.ErrorField;
import com.test.rest.resource.ValidationErrorsResponse.ErrorGroup;
import org.springframework.core.convert.converter.Converter;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import java.util.List;
import java.util.Map.Entry;
import java.util.Objects;
import java.util.Set;
import java.util.function.Function;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static java.util.Comparator.comparing;
import static java.util.stream.Collectors.*;

@ControllerAdvice(annotations = Validated.class)
public class ValidationExceptionHandler {

    // javax.validation.Path has no API to retrieve index of source objects List
    private final Pattern indexPattern = Pattern.compile("^.+\\[(.+)\\].*$");

    private final Converter<ConstraintViolation<?>, ErrorField> messageConverter;

    public ValidationExceptionHandler(Converter<ConstraintViolation<?>,ErrorField> messageConverter) {
        this.messageConverter = Objects.requireNonNull(messageConverter);
    }

    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<ValidationErrorsResponse> handle(ConstraintViolationException exception) {
        List<ErrorGroup> errors = exception.getConstraintViolations().stream()
                .collect(groupingBy(index()))
                .entrySet().stream()
                    .sorted(comparing(Entry::getKey))
                    .map(toErrorGroup())
                    .collect(toList());

        return ResponseEntity.ok().body(ValidationErrorsResponse.builder().errors(errors).build());
    }

    private Function<Entry<Integer, List<ConstraintViolation<?>>>, ErrorGroup> toErrorGroup() {
        return entry -> {
            Set<ErrorField> errorFields = entry.getValue().stream()
                    .map(messageConverter::convert).collect(toSet());
            return new ErrorGroup(entry.getKey(), errorFields);
        };
    }

    private Function<ConstraintViolation<?>, Integer> index() {
        return violation -> {
            Matcher m = indexPattern.matcher(violation.getPropertyPath().toString());
            return m.find() ? Integer.parseInt(m.group(1)) : null;
        };
    }
}
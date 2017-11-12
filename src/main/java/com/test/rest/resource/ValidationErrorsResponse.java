package com.test.rest.resource;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.List;
import java.util.Set;

@Builder
@Data
public class ValidationErrorsResponse {
    @Builder.Default boolean valid = false;
    private List<ErrorGroup> errors;

    @AllArgsConstructor
    @Data
    public static class ErrorGroup {
        Integer index;
        Set<ErrorField> errors;
    }

    @AllArgsConstructor
    @Data
    @EqualsAndHashCode
    public static class ErrorField {
        String field;
        String error;
    }
}

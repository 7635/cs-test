package com.test.rest.validation.validators;

import com.test.rest.validation.annotation.DateAfterDate;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.beanutils.BeanUtils;

import javax.validation.ConstraintValidatorContext;
import java.lang.reflect.InvocationTargetException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

@Slf4j
public class DateAfterDateValidator extends BaseValidator<DateAfterDate, Object> {
    private String precedingDate;
    private String succeedingDate;

    @Override
    public void initialize(DateAfterDate constraintAnnotation) {
        precedingDate = constraintAnnotation.precedingDate();
        succeedingDate = constraintAnnotation.succeedingDate();
    }

    @Override
    public boolean isValid(Object value, ConstraintValidatorContext context) {
        try {
            LocalDate preceding = parse(BeanUtils.getProperty(value, precedingDate));
            LocalDate succeeding = parse(BeanUtils.getProperty(value, succeedingDate));

            if (succeeding == null) {
                return true;
            }

            if (preceding == null) {
                return false;
            }

            return preceding.isBefore(succeeding);

        } catch (IllegalAccessException | InvocationTargetException | NoSuchMethodException e) {
            log.error("Cannot read bean property", e);
            return false;
        }
    }

    private LocalDate parse(String candidate) {
        return candidate != null ? LocalDate.parse(candidate, DateTimeFormatter.ISO_LOCAL_DATE) : null;
    }
}
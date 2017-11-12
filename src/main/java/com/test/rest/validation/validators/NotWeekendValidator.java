package com.test.rest.validation.validators;

import com.test.rest.validation.annotation.NotWeekend;
import com.google.common.collect.Sets;

import javax.validation.ConstraintValidatorContext;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.Set;

public class NotWeekendValidator extends BaseValidator<NotWeekend, LocalDate> {
    private final Set<DayOfWeek> weekendDays = Sets.newHashSet(DayOfWeek.SATURDAY, DayOfWeek.SUNDAY);

    @Override
    public boolean isValid(LocalDate date, ConstraintValidatorContext context) {
        return date == null || !(weekendDays.contains(date.getDayOfWeek()));
    }
}

package com.test.rest.validation.validators;

import com.google.common.collect.Maps;
import com.test.rest.validation.annotation.OptionsType;
import com.test.rest.validation.annotation.OptionsType.Validation;
import com.test.rest.validation.ValidationConstraints;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;

import javax.validation.ConstraintValidatorContext;
import java.lang.reflect.InvocationTargetException;
import java.time.LocalDate;
import java.util.Currency;
import java.util.Map;
import java.util.Objects;
import java.util.function.Predicate;
import java.util.stream.Stream;

import static com.test.rest.validation.annotation.OptionsType.Validation.*;
import static java.time.format.DateTimeFormatter.ISO_LOCAL_DATE;
import static org.apache.commons.beanutils.BeanUtils.getProperty;

@Slf4j
public class OptionsTypeValidator extends BaseValidator<OptionsType, Object> {
    private Map<Validation, Predicate<Object>> validators = Maps.newHashMap();
    {
        validators.put(STYLE, styleWithOptions());
        validators.put(EXCERCISE_START_DATE, excerciseStartDateWithAmericanOptions());
        validators.put(DELIVERY_DATE, deliveryDateWithOptions());
        validators.put(CURRENCY, currencyISO4217());
    }

    private Fields fields = new Fields();

    private ValidationConstraints constraints;

    public OptionsTypeValidator(ValidationConstraints validationConstraints) {
        this.constraints = Objects.requireNonNull(validationConstraints);
    }
    @Override
    public void initialize(OptionsType constraintAnnotation) {
        fields.setValidationType(constraintAnnotation.constraint());
        fields.setTypeFieldName(constraintAnnotation.type());
        fields.setStyleFieldName(constraintAnnotation.style());
        fields.setExcStartDateFieldName(constraintAnnotation.excerciseStartDate());
        fields.setTradeDateFieldName(constraintAnnotation.tradeDate());
        fields.setExpireDateFieldName(constraintAnnotation.expiryDate());
        fields.setDeliveryDateFieldName(constraintAnnotation.deliveryDate());
        fields.setPremiumDateFieldName(constraintAnnotation.premiumDate());
        fields.setCurrencyFieldName(constraintAnnotation.currency());
    }

    @Override
    public boolean isValid(Object value, ConstraintValidatorContext context) {
        return validators.get(fields.getValidationType()).test(value);
    }

    private LocalDate parse(String candidate) {
        return (candidate != null) ? LocalDate.parse(candidate, ISO_LOCAL_DATE) : null;
    }

    private boolean nonNulls(Object ...objects) {
        return Stream.of(objects).noneMatch(Objects::isNull);
    }

    private Predicate<Object> styleWithOptions() {
        return o -> {
            try {
                final String typeValue = getProperty(o, fields.getTypeFieldName());
                if (constraints.getOptionsType().equals(typeValue)) {
                    String styleValue = getProperty(o, fields.getStyleFieldName());
                    return constraints.isValidOptionsStyle(styleValue);
                }
            } catch (IllegalAccessException | InvocationTargetException| NoSuchMethodException e) {
                log.error("Cannot read property value", e);
                return false;
            }
            return true;
        };
    }

    private Predicate<Object> excerciseStartDateWithAmericanOptions() {
        return o -> {
            try {
                String type = getProperty(o, fields.getTypeFieldName());
                String style = getProperty(o, fields.getStyleFieldName());

                if (constraints.getOptionsType().equals(type) && constraints.getAmericanStyle().equals(style)) {
                    LocalDate expDate = parse(getProperty(o, fields.getExpireDateFieldName()));
                    LocalDate tradeDate = parse(getProperty(o, fields.getTradeDateFieldName()));
                    LocalDate excerciseDate = parse(getProperty(o, fields.getExcStartDateFieldName()));
                    
                    if (nonNulls(expDate, tradeDate, excerciseDate)) {
                        return excerciseDate.isBefore(expDate) && excerciseDate.isAfter(tradeDate);
                    }

                    return false;
                }
            } catch (IllegalAccessException | InvocationTargetException| NoSuchMethodException e) {
                log.error("Cannot read property value", e);
                return false;
            }
            return true;
        };
    }

    private Predicate<Object> deliveryDateWithOptions() {
        return o -> {
            try {
                final String typeValue = getProperty(o, fields.getTypeFieldName());
                if (constraints.getOptionsType().equals(typeValue)) {
                    LocalDate deliveryDate = parse(getProperty(o, fields.getDeliveryDateFieldName()));
                    LocalDate expireDate = parse(getProperty(o, fields.getExpireDateFieldName()));
                    LocalDate premiumDate = parse(getProperty(o, fields.getPremiumDateFieldName()));

                    if (nonNulls(deliveryDate, expireDate, premiumDate)) {
                        return deliveryDate.isAfter(expireDate) && deliveryDate.isAfter(premiumDate);
                    }
                    return false;
                }
            } catch (IllegalAccessException | InvocationTargetException| NoSuchMethodException e) {
                log.error("Cannot read property value", e);
                return false;
            }
            return true;
        };
    }

    private Predicate<Object> currencyISO4217() {
        return o -> {
            try {
                final String typeValue = getProperty(o, fields.getTypeFieldName());
                if (constraints.getOptionsType().equals(typeValue)) {
                    String currencyCode = getProperty(o, fields.getCurrencyFieldName());
                    try {
                        Currency.getInstance(currencyCode);
                    } catch (Exception e) {
                        return false;
                    }
                }
                return true;
            } catch (IllegalAccessException | InvocationTargetException| NoSuchMethodException e) {
                log.error("Cannot read property value", e);
                return false;
            }
        };
    }

    @Data
    private static class Fields {
        private Validation validationType;
        private String typeFieldName;
        private String styleFieldName;
        private String excStartDateFieldName;
        private String tradeDateFieldName;
        private String expireDateFieldName;
        private String deliveryDateFieldName;
        private String premiumDateFieldName;
        private String currencyFieldName;
    }
}

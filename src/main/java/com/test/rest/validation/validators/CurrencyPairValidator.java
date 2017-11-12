package com.test.rest.validation.validators;

import com.test.rest.validation.annotation.ValidCurrencyPair;

import javax.validation.ConstraintValidatorContext;
import java.util.Currency;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class CurrencyPairValidator extends BaseValidator<ValidCurrencyPair, String> {
    private final Pattern pairPattern = Pattern.compile("^(\\w\\w\\w)(\\w\\w\\w)$");

    @Override
    public boolean isValid(String currencyPair, ConstraintValidatorContext context) {
        if (currencyPair != null) {
            Matcher matcher = pairPattern.matcher(currencyPair);
            if (matcher.find()) {
                try {
                    Currency.getInstance(matcher.group(1));
                    Currency.getInstance(matcher.group(2));

                    return true;

                } catch (Exception e) {

                    return false;
                }
            }
        }
        return false;
    }
}

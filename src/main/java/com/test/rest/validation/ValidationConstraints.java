package com.test.rest.validation;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.util.List;

import static com.google.common.collect.Lists.newArrayList;

@Data
@Component
@ConfigurationProperties(prefix="validation")
public class ValidationConstraints {
    private List<String> customers = newArrayList();
    private List<String> legalEntities = newArrayList();
    private List<String> optionsStyles = newArrayList();
    private String optionsType;
    private String americanStyle;

    public boolean isValidCustomer(String customer) {
        return customers.contains(customer);
    }

    public boolean isLegalEntity(String entity) {
        return legalEntities.contains(entity);
    }

    public boolean isValidOptionsStyle(String style) {
        return optionsStyles.contains(style);
    }

    public String getOptionsType() {
        return optionsType;
    }

    public String getAmericanStyle() {
        return americanStyle;
    }
}

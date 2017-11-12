package com.test.rest.resource;

import com.test.rest.validation.annotation.*;
import lombok.Data;

import java.time.LocalDate;

import static com.test.rest.validation.annotation.OptionsType.Validation.*;

@Data
@DateAfterDate(precedingDate="tradeDate",succeedingDate="valueDate",message="valueDate cannot be before tradeDate")
@OptionsType.Constraints({
        @OptionsType(constraint=EXCERCISE_START_DATE,
                message="excerciseStartDate should be after tradeDate and before expiryDate"),
        @OptionsType(constraint=STYLE, message="style should be AMERICAN or EUROPEAN"),
        @OptionsType(constraint=DELIVERY_DATE, message="deliveryDate should be after expiryDate(?) and premiumDate"),
        @OptionsType(constraint=CURRENCY, currency="payCcy", message="payCcy must be valid currency code"),
        @OptionsType(constraint=CURRENCY, currency="premiumCcy", message="premiumCcy must be valid currency code")
})
public class TradingDataRequest {

    private LocalDate tradeDate;

    @NotWeekend
    private LocalDate valueDate;

    private LocalDate excerciseStartDate;

    private LocalDate expiryDate;

    private LocalDate premiumDate;

    private LocalDate deliveryDate;

    @ValidCustomer
    private String customer;

    @ValidCurrencyPair
    private String ccyPair;

    private String payCcy;

    private String premiumCcy;

    private String type;

    private String style;

    @ValidLegalEntity
    private String legalEntity;

}
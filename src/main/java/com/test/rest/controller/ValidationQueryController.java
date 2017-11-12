package com.test.rest.controller;

import com.test.rest.resource.TradingDataRequest;
import com.test.rest.resource.ValidationSuccessResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.List;

@RestController
@Validated
public class ValidationQueryController {

    @PostMapping(value="/validate")
    public ResponseEntity<?> validate(@Valid @RequestBody List<TradingDataRequest> requestBody) {
        return ResponseEntity.ok(new ValidationSuccessResponse());
    }
}

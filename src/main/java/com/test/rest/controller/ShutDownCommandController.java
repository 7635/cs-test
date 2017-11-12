package com.test.rest.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.ExitCodeGenerator;
import org.springframework.boot.SpringApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import static java.util.Objects.requireNonNull;

@Slf4j
@RestController
public class ShutDownCommandController {

    private final ApplicationContext applicationContext;

    public ShutDownCommandController(ApplicationContext applicationContext) {
        this.applicationContext = requireNonNull(applicationContext);
    }

    @GetMapping("/shutdown")
    public void shutDown() {
        SpringApplication.exit(applicationContext, loggingShutDown());
    }

    private ExitCodeGenerator loggingShutDown() {
        log.info("Shutting down the application...");
        return () -> 0;
    }
}

package com.test.rest.controller;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class ValidationControllerTests {
    @Autowired
    private MockMvc mockMvc;

    private ResultActions post(String url, String filePath) throws Exception {
        String jsonContent = jsonFromFile(filePath);
        return this.mockMvc.perform(MockMvcRequestBuilders.post(url)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .content(jsonContent))
                .andDo(print());
    }

    private String jsonFromFile(String filePath) throws IOException {
        return new String(Files.readAllBytes(Paths.get(getClass().getResource(filePath).getPath())));
    }

    @Test
    public void shouldResponseHttp200WithNoErrors() throws Exception {
        post("/validate", "/requestValidData.json")
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk())
                .andExpect(content().json(jsonFromFile("/responseValid.json")));
    }


    @Test
    public void shouldDetectValidDateViolation() throws Exception {
        post("/validate", "/requestValueDateBeforeTradeDate.json")
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk())
                .andExpect(content().json(jsonFromFile("/responseValueDateBeforeTradeDate.json")));
    }

    @Test
    public void shouldDetectErrorsInBothElements() throws Exception {
        post("/validate", "/requestWithErrorsInDifferentElements.json")
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk())
                .andExpect(content().json(jsonFromFile("/responseWithErrorsInDifferentElements.json")));
    }

    @Test
    public void shouldDetectValueDateOccuringOnWeekend() throws Exception {
        post("/validate", "/requestValueDateDuringWeekend.json")
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk())
                .andExpect(content().json(jsonFromFile("/responseValueDateDuringWeekend.json")));
    }

    @Test
    public void shouldDetectInvalidCustomer() throws Exception {
        post("/validate", "/requestInvalidCustomer.json")
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk())
                .andExpect(content().json(jsonFromFile("/responseInvalidCustomer.json")));
    }

    @Test
    public void shouldDetectInvalidCurrency() throws Exception {
        post("/validate", "/requestInvalidCurrency.json")
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk())
                .andExpect(content().json(jsonFromFile("/responseInvalidCurrency.json")));
    }

    @Test
    public void shouldDetectInvalidStyleInOptionsType() throws Exception {
        post("/validate", "/requestInvalidStyleInOptions.json")
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk())
                .andExpect(content().json(jsonFromFile("/responseInvalidStyleInOptions.json")));
    }

    @Test
    public void shouldDetectInvalidExcerciseDateInAmericanOptions() throws Exception {
        post("/validate", "/requestInvalidExcersiseDateAmericanOptions.json")
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk())
                .andExpect(content().json(jsonFromFile("/responseInvalidExcerciseDateAmericanOptions.json")));
    }

    @Test
    public void shouldDetectInvalidDeliveryDateInOptionsType() throws Exception {
        post("/validate", "/requestInvalidDeliveryDateInOptions.json")
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk())
                .andExpect(content().json(jsonFromFile("/responseInvalidDeliveryDateInOptions.json")));
    }
}

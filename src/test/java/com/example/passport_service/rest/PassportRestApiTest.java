package com.example.passport_service.rest;

import com.example.passport_service.StubBuilder;
import com.example.passport_service.domain.Passport;
import com.example.passport_service.dto.PassportDto;
import com.example.passport_service.service.PassportMapperService;
import com.example.passport_service.service.PassportService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.SneakyThrows;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class PassportRestApiTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private PassportService<Passport> passportService;

    private final ObjectMapper objectMapper = new ObjectMapper();

    @Autowired
    private PassportMapperService passportMapper;

    @Test
    void save() throws Exception {
        final Long expectedId = 1L;
        final PassportDto passportDto = passportMapper.toDto(StubBuilder.builder().build());
        final String content = objectMapper.writeValueAsString(passportDto);
        when(passportService.save(any())).thenReturn(expectedId);

        mockMvc.perform(
                post("/passports/save")
                    .content(content)
                    .contentType(MediaType.APPLICATION_JSON)
            )
            .andExpect(
                content()
                    .contentType(MediaType.APPLICATION_JSON)
            )
            .andExpect(status().isOk())
            .andExpect(content().string("1"));
    }
}
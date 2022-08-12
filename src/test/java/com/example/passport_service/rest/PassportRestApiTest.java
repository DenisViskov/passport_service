package com.example.passport_service.rest;

import com.example.passport_service.StubBuilder;
import com.example.passport_service.config.DisabledDaoConfig;
import com.example.passport_service.domain.Passport;
import com.example.passport_service.dto.PassportDto;
import com.example.passport_service.config.RestApiConfigurationProperties;
import com.example.passport_service.service.PassportMapperService;
import com.example.passport_service.service.PassportService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;

import java.time.LocalDate;
import java.util.List;
import java.util.function.Supplier;
import java.util.stream.Collectors;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@Import(DisabledDaoConfig.class)
class PassportRestApiTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private RestApiConfigurationProperties pathHolder;

    @MockBean
    private PassportService<Passport> passportService;

    private final ObjectMapper objectMapper = new ObjectMapper();

    @Autowired
    private PassportMapperService passportMapper;

    @Test
    void saveTest() throws Exception {
        final Long expectedId = 1L;
        final PassportDto passportDto = passportMapper.toDto(StubBuilder.builder().build());
        final String content = objectMapper.writeValueAsString(passportDto);
        when(passportService.save(any())).thenReturn(expectedId);

        assertResponse(
            () -> post(pathHolder.getSave())
                .content(content)
                .contentType(MediaType.APPLICATION_JSON),
            String.valueOf(expectedId)
        );
    }

    @Test
    void updateTest() throws Exception {
        final PassportDto passportDto = passportMapper.toDto(StubBuilder.builder().build());
        final String content = objectMapper.writeValueAsString(passportDto);
        when(passportService.update(any())).thenReturn(true);

        assertResponse(
            () -> put(pathHolder.getUpdate())
                .content(content)
                .param("id", "1")
                .contentType(MediaType.APPLICATION_JSON),
            "true"
        );
    }

    @Test
    void deleteTest() throws Exception {
        when(passportService.delete(any())).thenReturn(true);

        assertResponse(
            () -> delete(pathHolder.getDelete())
                .param("id", "1")
                .contentType(MediaType.APPLICATION_JSON),
            "true"
        );
    }

    @Test
    void findTest() throws Exception {
        List<Passport> stubList = List.of(
            StubBuilder.builder().build(),
            StubBuilder.builder().name("TestName").build()
        );
        List<PassportDto> result = stubList.stream()
            .map(passportMapper::toDto)
            .collect(Collectors.toList());
        final String content = objectMapper.writeValueAsString(result);
        when(passportService.findAll()).thenReturn(stubList);

        assertResponse(
            () -> get(pathHolder.getFind())
                .contentType(MediaType.APPLICATION_JSON),
            content
        );
    }

    @Test
    void findBySerialTest() throws Exception {
        final Long serial = 1210L;
        List<Passport> stubList = List.of(
            StubBuilder.builder().serial(serial).build(),
            StubBuilder.builder().serial(serial).build()
        );
        List<PassportDto> result = stubList.stream()
            .map(passportMapper::toDto)
            .collect(Collectors.toList());
        final String content = objectMapper.writeValueAsString(result);
        when(passportService.findBySerial(serial)).thenReturn(stubList);

        assertResponse(
            () -> get(pathHolder.getFindBySerial())
                .param("serial", String.valueOf(serial))
                .contentType(MediaType.APPLICATION_JSON),
            content
        );
    }

    @Test
    void findUnavailableTest() throws Exception {
        List<Passport> stubList = List.of(
            StubBuilder.builder().expiredDate(LocalDate.now().minusMonths(1)).build(),
            StubBuilder.builder().expiredDate(LocalDate.now().minusMonths(2)).build()
        );
        List<PassportDto> result = stubList.stream()
            .map(passportMapper::toDto)
            .collect(Collectors.toList());
        final String content = objectMapper.writeValueAsString(result);
        when(passportService.findUnavailable()).thenReturn(stubList);

        assertResponse(
            () -> get(pathHolder.getUnavailable())
                .contentType(MediaType.APPLICATION_JSON),
            content
        );
    }

    @Test
    void findReplaceableTest() throws Exception {
        List<Passport> stubList = List.of(
            StubBuilder.builder().expiredDate(LocalDate.now().plusMonths(1)).build(),
            StubBuilder.builder().expiredDate(LocalDate.now().plusMonths(2)).build()
        );
        List<PassportDto> result = stubList.stream()
            .map(passportMapper::toDto)
            .collect(Collectors.toList());
        final String content = objectMapper.writeValueAsString(result);
        when(passportService.findReplaceable()).thenReturn(stubList);

        assertResponse(
            () -> get(pathHolder.getFindReplaceable())
                .contentType(MediaType.APPLICATION_JSON),
            content
        );
    }


    private <T extends MockHttpServletRequestBuilder> void assertResponse(
        final Supplier<T> builder, final String expectedContent
    ) throws Exception {
        mockMvc.perform(builder.get())
            .andExpect(
                content()
                    .contentType(MediaType.APPLICATION_JSON)
            )
            .andExpect(
                status()
                    .isOk())
            .andExpect(
                content()
                    .string(expectedContent)
            );
    }
}
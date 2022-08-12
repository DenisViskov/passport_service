package com.example.passport_service.config;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.ConstructorBinding;

@Getter
@AllArgsConstructor
@ConstructorBinding
@ConfigurationProperties(value = "spring.rest-api")
public class RestApiConfigurationProperties {
    private final String save;
    private final String update;
    private final String delete;
    private final String find;
    private final String findBySerial;
    private final String unavailable;
    private final String findReplaceable;
}

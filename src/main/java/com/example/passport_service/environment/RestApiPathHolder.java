package com.example.passport_service.environment;

import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Data
@Configuration
@EnableConfigurationProperties
public class RestApiPathHolder {

    @Value("${spring.restAPI.root}")
    private String root;

    @Value("${spring.restAPI.save}")
    private String save;
}

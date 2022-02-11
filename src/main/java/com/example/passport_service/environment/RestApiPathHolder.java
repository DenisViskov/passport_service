package com.example.passport_service.environment;

import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Data
@Configuration
@EnableConfigurationProperties
public class RestApiPathHolder {

    @Value("${spring.restAPI.save}")
    private String save;

    @Value("${spring.restAPI.update}")
    private String update;

    @Value("${spring.restAPI.delete}")
    private String delete;

    @Value("${spring.restAPI.find}")
    private String find;

    @Value("${spring.restAPI.findBySerial}")
    private String findBySerial;

    @Value("${spring.restAPI.unavailable}")
    private String unavailable;

    @Value("${spring.restAPI.findReplaceable}")
    private String findReplaceable;
}

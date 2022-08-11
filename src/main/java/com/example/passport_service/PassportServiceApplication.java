package com.example.passport_service;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@EnableScheduling
@SpringBootApplication
public class PassportServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(PassportServiceApplication.class, args);
    }

}

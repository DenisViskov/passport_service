package com.example.passport_service.service;

import com.example.passport_service.dto.ExpiredPassportDto;
import com.example.passport_service.store.PassportRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.stream.Collectors;

@Slf4j
@Component
public class ScheduledExpiredPassportNotifier {

    @Autowired
    private KafkaSender<ExpiredPassportDto> sender;

    @Autowired
    private PassportRepository repository;

    @Autowired
    private PassportMapperService mapper;

    @Value("${spring.kafka.topic}")
    private String topic;

    @Scheduled(fixedDelay = 1000)
    public void notifyExpired(){
        log.info("notifier started at: {}", LocalDateTime.now());
        final var expiredPassports = repository.findPassportsWithExpiredDate()
                .stream()
                .map(mapper::toExpiredPassportDto)
                .collect(Collectors.toList());

        log.info("sending [{}] expired passports", expiredPassports.size());
        expiredPassports.forEach(it -> sender.sendMessage(topic, it));
    }
}

package com.example.passport_service.service;

import com.example.passport_service.dto.ExpiredPassportDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;
import org.springframework.util.concurrent.ListenableFuture;

@Slf4j
@Service
public class ExpiredPassportSenderImpl implements KafkaSender<ExpiredPassportDto> {

    @Autowired
    private KafkaTemplate<Long, ExpiredPassportDto> kafkaTemplate;

    @Override public ListenableFuture<?> sendMessage(final String topic, final ExpiredPassportDto info) {
        log.info("send message to: {}", topic);
        log.debug("send expiredPassportDto: {}", info);
        return kafkaTemplate.send(topic, info);
    }
}

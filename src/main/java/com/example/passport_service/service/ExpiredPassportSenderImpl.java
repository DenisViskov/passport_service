package com.example.passport_service.service;

import com.example.passport_service.dto.ExpiredPassportDto;

public class ExpiredPassportSenderImpl implements KafkaSender<ExpiredPassportDto> {

    @Override public void sendMessage(final String topic, final ExpiredPassportDto info) {

    }
}

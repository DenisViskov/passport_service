package com.example.passport_service.service;

import org.springframework.util.concurrent.ListenableFuture;

public interface KafkaSender<T> {
    ListenableFuture<?> sendMessage(String topic, T info);
}

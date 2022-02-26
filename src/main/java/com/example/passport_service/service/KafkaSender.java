package com.example.passport_service.service;

public interface KafkaSender<T> {
    void sendMessage(String topic, T info);
}

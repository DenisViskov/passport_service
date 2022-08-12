package com.example.passport_service.service;

import com.example.passport_service.config.DisabledDaoConfig;
import com.example.passport_service.config.KafkaConfigurationProperties;
import com.example.passport_service.dto.ExpiredPassportDto;
import org.apache.kafka.clients.consumer.Consumer;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.common.serialization.LongDeserializer;
import org.assertj.core.api.WithAssertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.springframework.kafka.core.DefaultKafkaConsumerFactory;
import org.springframework.kafka.support.serializer.JsonDeserializer;
import org.springframework.kafka.test.EmbeddedKafkaBroker;
import org.springframework.kafka.test.context.EmbeddedKafka;
import org.springframework.kafka.test.utils.KafkaTestUtils;

import java.util.Collections;
import java.util.Map;

@EmbeddedKafka(
    partitions = 1,
    topics = "${spring.kafka.topic}",
    brokerProperties = {
        "listeners=PLAINTEXT://${spring.kafka.bootstrap-servers}",
        "port=${spring.kafka.port}"
    }
)
@SpringBootTest
@Import(DisabledDaoConfig.class)
class ExpiredPassportSenderImplTest implements WithAssertions {

    @Autowired
    private KafkaSender<ExpiredPassportDto> sender;

    private Consumer<Long, ExpiredPassportDto> consumer;

    @Autowired
    private EmbeddedKafkaBroker broker;

    @Autowired
    private KafkaConfigurationProperties kafkaConfigurationProperties;

    private final ExpiredPassportDto stub = ExpiredPassportDto.builder()
        .id(1L)
        .serial(1234L)
        .number(123456L)
        .build();

    @BeforeEach
    void consumer() {
        Map<String, Object> props = KafkaTestUtils.consumerProps(
                kafkaConfigurationProperties.getConsumer().getGroupId(), "true", broker
        );
        props.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, LongDeserializer.class);
        props.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, JsonDeserializer.class);
        props.put(JsonDeserializer.TRUSTED_PACKAGES, "*");
        props.put(JsonDeserializer.TYPE_MAPPINGS, kafkaConfigurationProperties.getTypeMapping());

        final var consumer = new DefaultKafkaConsumerFactory<Long, ExpiredPassportDto>(props)
                .createConsumer();
        consumer.subscribe(Collections.singletonList(kafkaConfigurationProperties.getTopic()));
        this.consumer = consumer;
    }

    @Test
    void sendMessage() {
        final var topic = kafkaConfigurationProperties.getTopic();
        final var listenableFuture = sender.sendMessage(topic, stub);
        final var record = KafkaTestUtils.getSingleRecord(consumer, topic);

        assertThat(listenableFuture.isDone()).isTrue();
        assertThat(record.topic()).isEqualTo(topic);
        assertThat(record.value()).isNotNull().isEqualTo(stub);
    }
}
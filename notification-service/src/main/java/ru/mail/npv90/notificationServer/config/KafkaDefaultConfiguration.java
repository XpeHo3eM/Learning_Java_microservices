package ru.mail.npv90.notificationServer.config;

import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

@Configuration
@Getter
public class KafkaDefaultConfiguration {
    @Value("${kafka.bootstrap.server}")
    private String bootstrapServers;

    @Value("${kafka.consumer.group-id}")
    private String groupId;

    @Value("${kafka.topic.order-created}")
    private String orderCreatedTopic;
}

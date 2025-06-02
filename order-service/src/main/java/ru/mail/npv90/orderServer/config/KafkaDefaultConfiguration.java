package ru.mail.npv90.orderServer.config;

import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
@Getter
public class KafkaDefaultConfiguration {
    @Value("${kafka.bootstrap.server}")
    private String bootstrapServers;

    @Value("${kafka.topic.order-created}")
    private String orderCreatedTopic;

    @Value("${kafka.topic.order-updated}")
    private String orderUpdatedTopic;

    @Value("${kafka.topic.order-deleted}")
    private String orderDeletedTopic;
}

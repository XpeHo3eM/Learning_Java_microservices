package ru.mail.npv90.orderServer.dal.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;
import ru.mail.npv90.general.dto.OrderDto;
import ru.mail.npv90.orderServer.config.KafkaDefaultConfiguration;

import java.util.List;
import java.util.UUID;


@Service
@RequiredArgsConstructor
public class KafkaOrderProducerService {
    private final KafkaDefaultConfiguration config;
    private final KafkaTemplate<String, OrderDto> template;

    public void sendOrderCreated(OrderDto orderDto) {
        send(config.getOrderCreatedTopic(), orderDto);
    }

    public void sendOrderCreated(List<OrderDto> orderDtos) {
        for (OrderDto orderDto : orderDtos) {
            sendOrderCreated(orderDto);
        }
    }

    public void sendOrderUpdated(OrderDto orderDto) {
        send(config.getOrderUpdatedTopic(), orderDto);
    }

    public void sendOrderDeleted(OrderDto orderDto) {
        send(config.getOrderDeletedTopic(), orderDto);
    }

    private void send(String topic, OrderDto orderDto) {
        template.send(topic,
                UUID.randomUUID().toString(),
                orderDto);
    }
}

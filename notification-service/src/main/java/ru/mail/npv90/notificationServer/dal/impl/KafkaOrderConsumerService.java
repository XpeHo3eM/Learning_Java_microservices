package ru.mail.npv90.notificationServer.dal.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;
import ru.mail.npv90.general.dto.OrderDto;
import ru.mail.npv90.notificationServer.dao.NotificationRepository;
import ru.mail.npv90.notificationServer.enums.Status;
import ru.mail.npv90.notificationServer.mapper.NotificationMapper;


@Service
@RequiredArgsConstructor
@Slf4j
public class KafkaOrderConsumerService {
    private final NotificationRepository repository;
    private final NotificationMapper mapper;

    @KafkaListener(topics = {"${kafka.topic.order-created}"},
            groupId = "${kafka.consumer.group-id}",
            containerFactory = "kafkaListenerContainerFactory")
    public void orderCreated(OrderDto orderDto) {
        log.info("Created: " + repository.create(mapper.toEntity(orderDto), Status.CREATED).toString());
    }

    @KafkaListener(topics = {"${kafka.topic.order-updated}"},
            groupId = "${kafka.consumer.group-id}",
            containerFactory = "kafkaListenerContainerFactory")
    public void orderUpdated(OrderDto orderDto) {
        log.info("Updated: " + repository.create(mapper.toEntity(orderDto), Status.UPDATED).toString());
    }

    @KafkaListener(topics = {"${kafka.topic.order-deleted}"},
            groupId = "${kafka.consumer.group-id}",
            containerFactory = "kafkaListenerContainerFactory")
    public void orderDeleted(OrderDto orderDto) {
        log.info("Deleted: " + repository.create(mapper.toEntity(orderDto), Status.DELETED).toString());
    }
}

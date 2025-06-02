package ru.mail.npv90.notificationServer.dao;

import ru.mail.npv90.notificationServer.enums.Status;
import ru.mail.npv90.notificationServer.model.NotificationEntity;

import java.util.Optional;

public interface NotificationRepository {
    NotificationEntity create(NotificationEntity notification, Status status);

    Optional<NotificationEntity> findById(Long id);

    Optional<NotificationEntity> update(NotificationEntity notification);

    void deleteById(Long id);
}

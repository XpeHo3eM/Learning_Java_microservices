package ru.mail.npv90.notificationServer.dal;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import ru.mail.npv90.general.dto.OrderDto;
import ru.mail.npv90.notificationServer.dto.NewNotificationDto;
import ru.mail.npv90.notificationServer.dto.NotificationDto;
import ru.mail.npv90.notificationServer.dto.UpdatedNotificationDto;

public interface NotificationService {
    OrderDto getInfo(@Valid @Positive Long id);
    NotificationDto create(@Valid @NotNull NewNotificationDto newNotificationDto);
    NotificationDto update(@Valid @Positive long id,
                           @Valid @NotNull UpdatedNotificationDto updatedNotificationDto);

    void deleteByid(@Valid @Positive Long id);
}

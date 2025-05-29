package ru.mail.npv90.notificationServer.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import ru.mail.npv90.general.dto.OrderDto;
import ru.mail.npv90.notificationServer.dto.NewNotificationDto;
import ru.mail.npv90.notificationServer.dto.NotificationDto;
import ru.mail.npv90.notificationServer.dto.UpdatedNotificationDto;
import ru.mail.npv90.notificationServer.model.NotificationEntity;

@Mapper(componentModel = "spring")
public interface NotificationMapper {
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "status", constant = "CREATED")
    @Mapping(target = "sendAt", expression = "java(java.time.LocalDateTime.now())")
    NotificationEntity toEntity(NewNotificationDto newNotification);

    @Mapping(target = "status", constant = "UPDATED")
    @Mapping(target = "sendAt", expression = "java(java.time.LocalDateTime.now())")
    NotificationEntity toEntity(UpdatedNotificationDto updatedNotificationDto);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "status", constant = "CREATED")
    @Mapping(target = "sendAt", expression = "java(java.time.LocalDateTime.now())")
    NotificationEntity toEntity(OrderDto orderDto);

    NotificationDto toDto(NotificationEntity notificationEntity);

    @Mapping(target = "filename", source = "url", qualifiedByName = "urlToFilename")
    OrderDto toOrderDto(NotificationEntity entity);

    @Named("urlToFilename")
    default String urlToFilename(String url) {
        return url.substring(url.lastIndexOf('/') + 1);
    }
}

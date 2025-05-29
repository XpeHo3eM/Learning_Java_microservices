package ru.mail.npv90.notificationServer.dal.impl;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;
import ru.mail.npv90.general.dto.OrderDto;
import ru.mail.npv90.notificationServer.dal.NotificationService;
import ru.mail.npv90.notificationServer.dao.NotificationRepository;
import ru.mail.npv90.notificationServer.dto.NewNotificationDto;
import ru.mail.npv90.notificationServer.dto.NotificationDto;
import ru.mail.npv90.notificationServer.dto.UpdatedNotificationDto;
import ru.mail.npv90.notificationServer.enums.Status;
import ru.mail.npv90.notificationServer.mapper.NotificationMapper;
import ru.mail.npv90.notificationServer.model.NotificationEntity;

@Service
@RequiredArgsConstructor
@Transactional
@Validated
public class NotificationServiceImpl implements NotificationService {
    private final NotificationRepository repository;
    private final NotificationMapper mapper;

    @Override
    public NotificationDto create(NewNotificationDto newNotificationDto) {
        return mapper.toDto(repository.create(mapper.toEntity(newNotificationDto), Status.CREATED));
    }

    @Override
    public OrderDto getInfo(Long id) {
        NotificationEntity entity = repository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException(String.format("Notification by id: %d not found", id)));

        return mapper.toOrderDto(entity);
    }

    @Override
    public NotificationDto update(long id,
                                  UpdatedNotificationDto updatedNotificationDto) {
        updatedNotificationDto.setId(id);

        NotificationEntity entity = repository.update(mapper.toEntity(updatedNotificationDto))
                .orElseThrow(() -> new EntityNotFoundException(String.format("Notification by id: %d not found", id)));

        return mapper.toDto(entity);
    }

    @Override
    public void deleteByid(Long id) {
        repository.deleteById(id);
    }
}

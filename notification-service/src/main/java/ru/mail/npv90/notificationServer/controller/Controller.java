package ru.mail.npv90.notificationServer.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import ru.mail.npv90.general.dto.OrderDto;
import ru.mail.npv90.notificationServer.dal.NotificationService;
import ru.mail.npv90.notificationServer.dto.NewNotificationDto;
import ru.mail.npv90.notificationServer.dto.NotificationDto;
import ru.mail.npv90.notificationServer.dto.UpdatedNotificationDto;

@RestController
@RequestMapping("/api/subscriptions")
@RequiredArgsConstructor
public class Controller {
    private final NotificationService service;

    @GetMapping("/{id}")
    public OrderDto get(@PathVariable Long id) {
        return service.getInfo(id);
    }

    @PostMapping
    public NotificationDto create(@RequestBody NewNotificationDto newNotificationDto) {
        return service.create(newNotificationDto);
    }

    @PatchMapping("/{id}")
    public NotificationDto update(@PathVariable Long id,
                                  @RequestBody UpdatedNotificationDto updatedNotificationDto) {
        return service.update(id, updatedNotificationDto);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        service.deleteByid(id);
    }
}

package ru.mail.npv90.notificationServer.dto;


import jakarta.validation.constraints.NotBlank;

public record NewNotificationDto(
        @NotBlank
        String url) {
}

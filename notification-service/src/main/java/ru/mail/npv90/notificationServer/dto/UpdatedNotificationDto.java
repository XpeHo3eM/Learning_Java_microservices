package ru.mail.npv90.notificationServer.dto;


import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class UpdatedNotificationDto {
    private Long id;

    @NotBlank
    private String url;
}

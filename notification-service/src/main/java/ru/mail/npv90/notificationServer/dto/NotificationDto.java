package ru.mail.npv90.notificationServer.dto;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.mail.npv90.notificationServer.enums.Status;

import java.io.Serializable;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class NotificationDto {
    private Long id;
    private String url;
    private Status status;
    private LocalDateTime sendAt;
}

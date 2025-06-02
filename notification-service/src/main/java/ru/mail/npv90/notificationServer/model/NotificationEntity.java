package ru.mail.npv90.notificationServer.model;


import jakarta.persistence.*;
import lombok.*;
import ru.mail.npv90.notificationServer.enums.Status;

import java.time.LocalDateTime;


@Entity
@Table(name = "notifications")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class NotificationEntity {
    @Id
    @Column(name = "id")
    public Long id;

    @Column(name = "url")
    @EqualsAndHashCode.Exclude
    public String url;

    @Column(name = "status")
    @Enumerated(EnumType.STRING)
    @EqualsAndHashCode.Exclude
    private Status status;

    @Column(name = "send_at")
    @EqualsAndHashCode.Exclude
    public LocalDateTime sendAt;
}

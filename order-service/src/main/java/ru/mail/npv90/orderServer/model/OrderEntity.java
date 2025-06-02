package ru.mail.npv90.orderServer.model;


import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.*;

import java.time.LocalDateTime;


@Entity
@Table(name = "orders")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class OrderEntity {
    @Id
    @Column(name = "url")
    public String url;

    @Column(name = "file_name")
    @EqualsAndHashCode.Exclude
    public String filename;

    @Column(name = "created_at")
    @EqualsAndHashCode.Exclude
    public LocalDateTime createdAt;
}

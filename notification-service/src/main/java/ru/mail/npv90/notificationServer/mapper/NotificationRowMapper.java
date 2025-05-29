package ru.mail.npv90.notificationServer.mapper;

import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;
import ru.mail.npv90.notificationServer.enums.Status;
import ru.mail.npv90.notificationServer.model.NotificationEntity;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;

@Component
public class NotificationRowMapper implements RowMapper<NotificationEntity> {
    @Override
    public NotificationEntity mapRow(ResultSet rs, int rowNum) throws SQLException {
        return NotificationEntity.builder()
                .id(rs.getLong("id"))
                .url(rs.getString("url"))
                .status(Status.valueOf(rs.getString("status")))
                .sendAt(rs.getObject("send_at", LocalDateTime.class))
                .build();
    }
}

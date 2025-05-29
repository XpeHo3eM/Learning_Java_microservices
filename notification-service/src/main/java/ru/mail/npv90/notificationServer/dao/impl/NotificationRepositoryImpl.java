package ru.mail.npv90.notificationServer.dao.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import ru.mail.npv90.notificationServer.dao.NotificationRepository;
import ru.mail.npv90.notificationServer.enums.Status;
import ru.mail.npv90.notificationServer.mapper.NotificationRowMapper;
import ru.mail.npv90.notificationServer.model.NotificationEntity;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
@Transactional
public class NotificationRepositoryImpl implements NotificationRepository {
    private final JdbcTemplate jdbcTemplate;
    private final NotificationRowMapper rowMapper;

    @Override
    public NotificationEntity create(NotificationEntity notification, Status status) {
        Map<String, Object> mapping = new HashMap<>() {{
            put("id", notification.getId());
            put("url", notification.url);
            put("status", status);
            put("send_at", notification.sendAt);
        }};

        long id = new SimpleJdbcInsert(jdbcTemplate)
                .withTableName("notifications")
                .usingGeneratedKeyColumns("id")
                .executeAndReturnKey(mapping)
                .longValue();

        notification.setId(id);
        notification.setStatus(status);

        return notification;
    }

    @Override
    public Optional<NotificationEntity> findById(Long id) {
        final String sql = """
                SELECT *
                FROM notifications
                WHERE id = ?
                """;

        try {
            return Optional.of(jdbcTemplate.queryForObject(sql, rowMapper, id));
        } catch (DataAccessException e) {
            return Optional.empty();
        }
    }

    @Override
    public Optional<NotificationEntity> update(NotificationEntity notification) {
        final String sql = """
                UPDATE notifications
                SET url = ?,
                    status = ?,
                    send_at = ?
                WHERE id = ?
                """;

        notification.setStatus(Status.UPDATED);

        if (jdbcTemplate.update(sql,
                notification.getUrl(),
                notification.getStatus().toString(),
                LocalDateTime.now(),
                notification.getId()) == 0) {
            return Optional.empty();
        } else {
            return Optional.of(notification);
        }
    }

    @Override
    public void deleteById(Long id) {
        final String sql = """
                DELETE FROM notifications
                WHERE id = ?
                """;

        jdbcTemplate.update(sql, id);
    }
}

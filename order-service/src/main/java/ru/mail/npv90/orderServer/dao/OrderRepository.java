package ru.mail.npv90.orderServer.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.mail.npv90.orderServer.model.OrderEntity;

import java.util.Optional;

public interface OrderRepository extends JpaRepository<OrderEntity, Long> {
    Optional<OrderEntity> findByUrl(String url);
    boolean existsByUrl(String url);
    void deleteByUrl(String url);
}

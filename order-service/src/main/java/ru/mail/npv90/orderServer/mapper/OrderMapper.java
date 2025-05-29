package ru.mail.npv90.orderServer.mapper;

import org.mapstruct.Mapper;
import ru.mail.npv90.general.dto.OrderDto;
import ru.mail.npv90.orderServer.model.OrderEntity;

import java.util.List;

@Mapper(componentModel = "spring")
public interface OrderMapper {
    OrderDto toDto(OrderEntity orderEntity);

    List<OrderDto> toDto(List<OrderEntity> orderEntities);
}

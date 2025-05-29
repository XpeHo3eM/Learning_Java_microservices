package ru.mail.npv90.orderServer.dal;

import org.springframework.web.multipart.MultipartFile;
import ru.mail.npv90.general.dto.OrderDto;

import java.util.List;

public interface OrderService {
    OrderDto upload(MultipartFile file);

    List<OrderDto> upload(List<MultipartFile> files);

    OrderDto update(String bucket, String key, MultipartFile file);

    void delete(String bucket, String key);
}

package ru.mail.npv90.orderServer.dal.impl;

import jakarta.persistence.EntityExistsException;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import ru.mail.npv90.general.dto.OrderDto;
import ru.mail.npv90.orderServer.dal.OrderService;
import ru.mail.npv90.orderServer.dal.S3Service;
import ru.mail.npv90.orderServer.dao.OrderRepository;
import ru.mail.npv90.orderServer.mapper.OrderMapper;
import ru.mail.npv90.orderServer.model.OrderEntity;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
@Slf4j
public class OrderServiceImpl implements OrderService {
    private final OrderRepository orderRepository;
    private final OrderMapper mapper;
    private final S3Service s3Service;
    private final KafkaOrderProducerService producer;

    @Override
    public OrderDto upload(MultipartFile file) {
        assertEntityExists(file);

        String url;
        try {
            url = s3Service.upload(file);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        OrderEntity orderEntity = createOrderEntity(url);
        orderRepository.save(orderEntity);

        OrderDto orderDto = mapper.toDto(orderEntity);
        producer.sendOrderCreated(orderDto);

        return orderDto;
    }

    @Override
    public List<OrderDto> upload(List<MultipartFile> files) {
        assertEntityExists(files);

        List<String> urls;
        try {
            urls = s3Service.upload(files);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        List<OrderEntity> orderEntities = new ArrayList<>();
        for (String url : urls) {
            orderEntities.add(createOrderEntity(url));
        }

        orderRepository.saveAll(orderEntities);

        List<OrderDto> orderDtos = mapper.toDto(orderEntities);
        producer.sendOrderCreated(orderDtos);

        return orderDtos;
    }

    @Override
    public OrderDto update(String bucket, String key, MultipartFile newFile) {
        OrderEntity orderEntity = getEntityByUrlOrThrowException(s3Service.generateUrl(bucket, key));

        try {
            s3Service.update(bucket, key, newFile);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        orderEntity.setCreatedAt(LocalDateTime.now());

        OrderDto orderDto = mapper.toDto(orderEntity);
        producer.sendOrderUpdated(orderDto);

        return orderDto;
    }

    @Override
    public void delete(String bucket, String key) {
        String url = s3Service.delete(bucket, key);
        OrderEntity deletedOrder = getEntityByUrlOrThrowException(url);

        orderRepository.deleteByUrl(url);
        producer.sendOrderDeleted(mapper.toDto(deletedOrder));
    }

    private OrderEntity createOrderEntity(String url) {
        String filename = url.substring(url.lastIndexOf('/') + 1);

        return OrderEntity.builder()
                .filename(filename)
                .url(url)
                .createdAt(LocalDateTime.now())
                .build();
    }

    private OrderEntity getEntityByUrlOrThrowException(String url) {
        return orderRepository.findByUrl(url)
                .orElseThrow(() -> new EntityNotFoundException(String.format("Ссылка: %s не найдена", url)));
    }

    private void assertEntityExists(MultipartFile file) {
        String url = s3Service.generateUrl(file.getOriginalFilename());

        if (orderRepository.existsByUrl(url)) {
            throw new EntityExistsException(url + " уже существует");
        }
    }

    private void assertEntityExists(List<MultipartFile> files) {
        List<String> existsFiles = new LinkedList<>();

        for (MultipartFile file : files) {
            try {
                assertEntityExists(file);
            } catch (EntityExistsException e) {
                existsFiles.add(file.getOriginalFilename());
            }
        }

        if (!existsFiles.isEmpty()) {
            throw new EntityExistsException(existsFiles + " уже существуют");
        }
    }
}

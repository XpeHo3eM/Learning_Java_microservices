package ru.mail.npv90.orderServer.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import ru.mail.npv90.general.dto.OrderDto;
import ru.mail.npv90.orderServer.dal.OrderService;

import java.util.List;

import static org.springframework.http.MediaType.MULTIPART_FORM_DATA_VALUE;

@RestController
@RequestMapping("/api/orders")
@RequiredArgsConstructor
public class Controller {
    private final OrderService service;

    @PostMapping(consumes = MULTIPART_FORM_DATA_VALUE)
    @ResponseStatus(HttpStatus.CREATED)
    public OrderDto upload(@RequestParam MultipartFile file) {
        return service.upload(file);
    }

    @PostMapping(value = "/multi", consumes = MULTIPART_FORM_DATA_VALUE)
    @ResponseStatus(HttpStatus.CREATED)
    public List<OrderDto> upload(@RequestParam List<MultipartFile> files) {
        return service.upload(files);
    }

    @PatchMapping(consumes = MULTIPART_FORM_DATA_VALUE)
    @ResponseStatus(HttpStatus.ACCEPTED)
    public OrderDto update(@RequestParam String bucket,
                           @RequestParam String key,
                           @RequestParam MultipartFile updatedFile) {
        return service.update(bucket, key, updatedFile);
    }

    @DeleteMapping
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@RequestParam String bucket,
                       @RequestParam String key) {
        service.delete(bucket, key);
    }
}

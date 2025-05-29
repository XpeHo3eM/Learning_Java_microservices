package ru.mail.npv90.orderServer.config;

import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
@Getter
public class S3Configuration {
    @Value("${s3.region}")
    private String region;

    @Value("${s3.key-id}")
    private String keyID;

    @Value("${s3.key-secret}")
    private String secretKey;

    @Value("${s3.bucket-name}")
    private String bucket;

    @Value("${s3.folder}")
    private String folder;

    @Value("${s3.endpoint}")
    private String endpoint;
}

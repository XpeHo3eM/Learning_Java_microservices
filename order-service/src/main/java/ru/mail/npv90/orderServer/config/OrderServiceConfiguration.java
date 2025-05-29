package ru.mail.npv90.orderServer.config;


import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import software.amazon.awssdk.auth.credentials.AwsBasicCredentials;
import software.amazon.awssdk.auth.credentials.AwsCredentials;
import software.amazon.awssdk.auth.credentials.StaticCredentialsProvider;
import software.amazon.awssdk.http.apache.ApacheHttpClient;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.s3.S3Client;

import java.net.URI;


@Configuration
@RequiredArgsConstructor
public class OrderServiceConfiguration {
    private final S3Configuration s3Configuration;

    @Bean
    public AwsCredentials awsCredentials() {
        return AwsBasicCredentials.create(s3Configuration.getKeyID(), s3Configuration.getSecretKey());
    }

    @Bean
    public S3Client s3Client(AwsCredentials credentials) {
        return S3Client.builder()
                .httpClient(ApacheHttpClient.create())
                .region(Region.of(s3Configuration.getRegion()))
                .endpointOverride(URI.create(s3Configuration.getEndpoint()))
                .credentialsProvider(StaticCredentialsProvider.create(credentials))
                .build();
    }
}

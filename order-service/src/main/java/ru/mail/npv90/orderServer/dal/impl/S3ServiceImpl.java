package ru.mail.npv90.orderServer.dal.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import ru.mail.npv90.orderServer.config.S3Configuration;
import ru.mail.npv90.orderServer.dal.S3Service;
import software.amazon.awssdk.core.sync.RequestBody;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.DeleteObjectRequest;
import software.amazon.awssdk.services.s3.model.PutObjectRequest;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;


@Service
@RequiredArgsConstructor
public class S3ServiceImpl implements S3Service {
    private final S3Configuration s3Configuration;
    private final S3Client s3Client;

    @Override
    public String upload(MultipartFile file) throws IOException {
        String key = s3Configuration.getFolder() + "/" + file.getOriginalFilename();
        PutObjectRequest request = PutObjectRequest.builder()
                .key(key)
                .bucket(s3Configuration.getBucket())
                .contentType(file.getContentType())
                .build();

        s3Client.putObject(request, RequestBody.fromBytes(file.getBytes()));

        return toUrl(s3Configuration.getBucket(), key);
    }

    @Override
    public List<String> upload(List<MultipartFile> files) throws IOException {
        List<String> keys = new ArrayList<>();

        for (MultipartFile file : files) {
            keys.add(upload(file));
        }

        return keys;
    }

    @Override
    public String update(String bucket, String key, MultipartFile file) throws IOException {
        PutObjectRequest putRequest = PutObjectRequest.builder()
                .bucket(bucket)
                .key(key)
                .build();

        s3Client.putObject(putRequest, RequestBody.fromBytes(file.getBytes()));

        return toUrl(bucket, key);
    }

    @Override
    public String delete(String bucket, String key) {
        DeleteObjectRequest deleteRequest = DeleteObjectRequest.builder()
                .bucket(bucket)
                .key(key)
                .build();

        s3Client.deleteObject(deleteRequest);

        return toUrl(bucket, key);
    }

    private String toUrl(String bucket, String key) {
        return String.format("%s/%s/%s",
                s3Configuration.getEndpoint(),
                bucket,
                key);
    }

    @Override
    public String generateUrl(String filename) {
        return String.format("%s/%s/%s/%s",
                s3Configuration.getEndpoint(),
                s3Configuration.getBucket(),
                s3Configuration.getFolder(),
                filename);
    }

    @Override
    public String generateUrl(String bucket, String key) {
        return toUrl(bucket, key);
    }
}

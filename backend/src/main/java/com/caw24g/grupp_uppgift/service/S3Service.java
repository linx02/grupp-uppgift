package com.caw24g.grupp_uppgift.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import software.amazon.awssdk.auth.credentials.AwsBasicCredentials;
import software.amazon.awssdk.auth.credentials.StaticCredentialsProvider;
import software.amazon.awssdk.core.sync.RequestBody;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.ObjectCannedACL;
import software.amazon.awssdk.services.s3.model.PutObjectRequest;

import java.io.IOException;
import java.io.InputStream;
import java.util.UUID;

@Service
public class S3Service {
    private final S3Client s3Client;

    private final String bucketName;

    public S3Service(@Value("${aws.accessKey}") String accessKey,
                     @Value("${aws.secretKey}") String secretKey,
                     @Value("${aws.region}") String region,
                     @Value("${aws.s3.bucket}")String bucketName)
    {
        this.bucketName = bucketName;

        this.s3Client = S3Client.builder()
                .region(Region.of(region))
                .credentialsProvider(StaticCredentialsProvider.create(
                        AwsBasicCredentials.create(accessKey, secretKey)
                ))
                .build();
    }

    public String uploadFile(String fileName, InputStream fileStream, String contentType) throws IOException {
        String key = UUID.randomUUID() + "-" + fileName;

        PutObjectRequest putRequest = PutObjectRequest.builder()
                .bucket(bucketName)
                .key(key)
                .contentType(contentType)
                //.acl(ObjectCannedACL.PUBLIC_READ)
                .build();

        s3Client.putObject(putRequest, software.amazon.awssdk.core.sync.RequestBody.fromInputStream(fileStream,
                fileStream.available()));

        return "https://" + bucketName + ".s3.amazonaws.com/" + key;
    }

}

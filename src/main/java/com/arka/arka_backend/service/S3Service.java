package com.arka.arka_backend.service;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.PutObjectRequest;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import software.amazon.awssdk.auth.credentials.AwsBasicCredentials;
import software.amazon.awssdk.auth.credentials.StaticCredentialsProvider;
import software.amazon.awssdk.regions.Region;

import software.amazon.awssdk.services.s3.model.DeleteObjectRequest;

import java.io.IOException;
import java.util.UUID;

@Service
public class S3Service {

    private final S3Client s3Client;

    @Value("${aws.s3.bucket}")
    private String bucketName;

    @Value("${aws.region}")
    private String region;

    public S3Service(
            @Value("${aws.access.key}") String accessKey,
            @Value("${aws.secret.key}") String secretKey,
            @Value("${aws.region}") String region
    ) {

        AwsBasicCredentials credentials =
                AwsBasicCredentials.create(accessKey, secretKey);

        this.s3Client = S3Client.builder()
                .region(Region.of(region))
                .credentialsProvider(
                        StaticCredentialsProvider.create(credentials))
                .build();
    }


    // Upload
    public String uploadFile(MultipartFile file) throws IOException {

        String fileName =
                UUID.randomUUID() + "_" + file.getOriginalFilename();

        PutObjectRequest putObjectRequest =
                PutObjectRequest.builder()
                        .bucket(bucketName)
                        .key(fileName)
                        .contentType(file.getContentType())
                        .build();

        s3Client.putObject(
                putObjectRequest,
                software.amazon.awssdk.core.sync.RequestBody
                        .fromBytes(file.getBytes())
        );

        return "https://" + bucketName +
                ".s3." + region +
                ".amazonaws.com/" + fileName;
    }


    // DELETE FROM S3
    public void deleteFile(String imageUrl) {

        try {

            String fileName =
                    imageUrl.substring(imageUrl.lastIndexOf("/") + 1);

            DeleteObjectRequest deleteObjectRequest =
                    DeleteObjectRequest.builder()
                            .bucket(bucketName)
                            .key(fileName)
                            .build();

            s3Client.deleteObject(deleteObjectRequest);

            System.out.println("Deleted from S3: " + fileName);

        } catch (Exception e) {

            System.out.println("S3 delete failed");
            e.printStackTrace();

        }
    }

}
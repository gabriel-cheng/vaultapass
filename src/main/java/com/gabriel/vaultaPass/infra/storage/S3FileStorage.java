package com.gabriel.vaultaPass.infra.storage;

import java.io.IOException;
import java.io.InputStream;
import java.time.Duration;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.gabriel.vaultaPass.domain.user.FileStorage;
import com.gabriel.vaultaPass.exception.ErrorMessageEnum;

import software.amazon.awssdk.core.sync.RequestBody;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.DeleteObjectRequest;
import software.amazon.awssdk.services.s3.model.GetObjectRequest;
import software.amazon.awssdk.services.s3.model.PutObjectRequest;
import software.amazon.awssdk.services.s3.model.S3Exception;
import software.amazon.awssdk.services.s3.presigner.S3Presigner;
import software.amazon.awssdk.services.s3.presigner.model.GetObjectPresignRequest;

@Component
public class S3FileStorage implements FileStorage {

    private final S3Client s3Client;
    private final S3Presigner s3Presigner;
    private final String bucket;

    public S3FileStorage(
        S3Client s3Client,
        S3Presigner s3Presigner,
        @Value("${app.storage.s3.bucket}") String bucket
    ) {
        this.s3Client = s3Client;
        this.s3Presigner = s3Presigner;
        this.bucket = bucket;
    }

    @Override
    public String upload(String fileName, InputStream content, String contentType) {
        try {
            String extension = fileName != null && fileName.contains(".")
                ? fileName.substring(fileName.lastIndexOf("."))
                : "";

            String objectKey = "profile-photos/" + UUID.randomUUID() + extension;

            byte[] bytes = content.readAllBytes();

            PutObjectRequest request = PutObjectRequest.builder()
                .bucket(bucket)
                .key(objectKey)
                .contentType(contentType)
                .contentLength((long) bytes.length)
                .build();

            s3Client.putObject(request, RequestBody.fromBytes(bytes));

            return objectKey;
        } catch(IOException | S3Exception ex) {
            throw new IllegalStateException(ErrorMessageEnum.FILE_UPLOAD_ERROR.getMessage());
        }
    }

    @Override
    public void delete(String objectKey) {
        try {
            DeleteObjectRequest request = DeleteObjectRequest.builder()
                .bucket(bucket)
                .key(objectKey)
                .build();

                s3Client.deleteObject(request);
        } catch(S3Exception ex) {
            throw new IllegalStateException(ErrorMessageEnum.DELETE_FILE_ERROR.getMessage());
        }
    }

    @Override
    public String generatePresignedUrl(String objectKet, Duration expiration) {
        GetObjectRequest getObjectRequest = GetObjectRequest.builder()
            .bucket(bucket)
            .key(objectKet)
            .build();

        GetObjectPresignRequest presignRequest = GetObjectPresignRequest.builder()
            .signatureDuration(expiration)
            .getObjectRequest(getObjectRequest)
            .build();

        return s3Presigner.presignGetObject(presignRequest).url().toString();
    }


}

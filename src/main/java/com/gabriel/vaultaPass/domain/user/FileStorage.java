package com.gabriel.vaultaPass.domain.user;

import java.io.InputStream;
import java.time.Duration;

public interface FileStorage {

    String upload(String fileName, InputStream content, String contentType);
    void delete(String objectKey);
    String generatePresignedUrl(String objectKey, Duration expiration);

}

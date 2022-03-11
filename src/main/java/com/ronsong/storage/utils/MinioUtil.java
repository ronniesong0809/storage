package com.ronsong.storage.utils;

import io.minio.MinioClient;
import io.minio.PutObjectArgs;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.InputStream;

/**
 * @author ronsong
 */
@Component
public class MinioUtil {
    @Autowired
    private MinioClient minioClient;

    @Value("${MINIO_BUCKET_NAME}")
    private String bucketName;

    public void upload(MultipartFile file, String fileName) {
        try {
            InputStream inputStream = file.getInputStream();
            minioClient.putObject(PutObjectArgs.builder()
                    .bucket(bucketName)
                    .object(fileName)
                    .stream(inputStream, file.getSize(), -1)
                            .contentType(file.getContentType())
                            .build());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

package com.ronsong.storage.utils;

import io.minio.*;
import io.minio.http.Method;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * @author ronsong
 */
@Component
public class MinioUtil {
    @Autowired
    private MinioClient minioClient;

    @Value("${MINIO_BUCKET_NAME}")
    private String bucketName;

    public List<String> upload(MultipartFile[] multipartFile) {
        List<String> fileNames = new ArrayList<>(multipartFile.length);

        for (MultipartFile file : multipartFile) {
            String fileName = file.getOriginalFilename();
            InputStream inputStream = null;
            try {
                inputStream = file.getInputStream();
                minioClient.putObject(PutObjectArgs.builder()
                        .bucket(bucketName)
                        .object(fileName)
                        .stream(inputStream, file.getSize(), -1)
                        .contentType(file.getContentType())
                        .build());
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                if (inputStream != null) {
                    try {
                        inputStream.close();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
            fileNames.add(fileName);
        }
        return fileNames;
    }

    public String getUrl(String fileName, int time, TimeUnit timeUnit) {
        try {
            return minioClient.getPresignedObjectUrl(GetPresignedObjectUrlArgs.builder()
                    .method(Method.GET)
                    .bucket(bucketName)
                    .object(fileName)
                    .expiry(time, timeUnit)
                    .build());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}

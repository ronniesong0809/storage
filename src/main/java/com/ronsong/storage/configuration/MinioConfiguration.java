package com.ronsong.storage.configuration;

import io.minio.MinioClient;
import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author ronsong
 */
@Configuration
@ConfigurationProperties(prefix = "minio")
@Data
public class MinioConfiguration {
    private String accessKey;
    private String secretKey;
    private String url;
    private String bucketName;

    @Bean
    public MinioClient minioClient() {
        return MinioClient.builder()
                .endpoint(url)
                .credentials(accessKey, secretKey)
                .build();
    }
}

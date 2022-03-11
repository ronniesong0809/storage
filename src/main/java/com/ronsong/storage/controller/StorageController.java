package com.ronsong.storage.controller;

import com.ronsong.storage.utils.MinioUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

/**
 * @author ronsong
 */
@RestController
public class StorageController {
    @Autowired
    private MinioUtil minioUtil;

    @PostMapping("/upload")
    public ResponseEntity<Object> upload(@RequestParam("file") MultipartFile[] file) {
        List<String> fileNames = minioUtil.upload(file);

        List<Map<String, String>> fileNamesMap = fileNames.stream()
                .map(fileName -> Map.of("fileName", fileName, "url", minioUtil.getUrl(fileName, 7 , TimeUnit.DAYS)))
                .collect(Collectors.toList());

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(fileNamesMap);
    }

    @GetMapping("/{fileName}")
    public ResponseEntity<Object> getUrl(@PathVariable("fileName") String fileName) {
        String url = minioUtil.getUrl(fileName, 7 , TimeUnit.DAYS);

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(url);
    }
}

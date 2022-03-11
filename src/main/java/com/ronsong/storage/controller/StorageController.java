package com.ronsong.storage.controller;

import com.ronsong.storage.utils.MinioUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.concurrent.TimeUnit;

/**
 * @author ronsong
 */
@RestController
public class StorageController {
    @Autowired
    private MinioUtil minioUtil;

    @PostMapping("/upload")
    public ResponseEntity<Object> upload(@RequestParam("file") MultipartFile file, @RequestParam("fileName") String fileName) {
        minioUtil.upload(file, fileName);
        String url = minioUtil.getUrl(fileName, 7 , TimeUnit.DAYS);

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(url);
    }

    @GetMapping("/{fileName}")
    public ResponseEntity<Object> getUrl(@PathVariable("fileName") String fileName) {
        String url = minioUtil.getUrl(fileName, 7 , TimeUnit.DAYS);

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(url);
    }
}

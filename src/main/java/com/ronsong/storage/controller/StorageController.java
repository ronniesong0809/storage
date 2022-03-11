package com.ronsong.storage.controller;

import com.ronsong.storage.utils.MinioUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

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
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body("upload success");
    }
}

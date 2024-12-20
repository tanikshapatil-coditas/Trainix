package com.example.trainix.controller;

import com.example.trainix.dto.ImageUploadDto;
import com.example.trainix.service.impl.ImageServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;

@RestController
@RequestMapping("/api/images")
public class ImageController {

    @Autowired
    private ImageServiceImpl image;

    @Value("${upload.path}")
    private String uploadPath;

    @PostMapping("/upload")
    public ResponseEntity<ImageUploadDto> uploadImage(@RequestParam("file") MultipartFile file) {
        try {
            String fileName = System.currentTimeMillis() + "_" + file.getOriginalFilename();
            File path = new File(uploadPath + File.separator + fileName);
            file.transferTo(path);
            ImageUploadDto response = new ImageUploadDto(path.getAbsolutePath());
            return ResponseEntity.ok(response);
        } catch (IOException e) {
            return ResponseEntity.status(500).body(new ImageUploadDto("Error uploading file"));
        }
    }

    @GetMapping("/view")
    public ResponseEntity<byte[]> viewImage(@RequestParam("path") String path) {
        try {
            path = URLDecoder.decode(path, StandardCharsets.UTF_8.toString());
            File imgFile = new File(path);
            if (!imgFile.exists()) {
                return ResponseEntity.notFound().build();
            }
            byte[] imageBytes = Files.readAllBytes(imgFile.toPath());
            return ResponseEntity.ok()
                    .header(HttpHeaders.CONTENT_TYPE, MediaType.IMAGE_PNG_VALUE)
                    .body(imageBytes);
        } catch (IOException e) {
            return ResponseEntity.status(500).body(null);
        }
    }
}

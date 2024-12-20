package com.example.trainix.service;

import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

public interface ImageService {
    public String uploadImage(MultipartFile file, Long userId) throws IOException;
    public byte[] viewImage(String imagePath) throws IOException;
}

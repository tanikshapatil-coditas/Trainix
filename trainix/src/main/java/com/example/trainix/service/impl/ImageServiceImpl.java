package com.example.trainix.service.impl;

import com.example.trainix.service.ImageService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

@Service
public class ImageServiceImpl implements ImageService {

    @Value("${upload.path}")
    private String uploadPath;

    public String uploadImage(MultipartFile file, Long userId) throws IOException {
        String fileName = userId + "_" + System.currentTimeMillis() + "_" + file.getOriginalFilename();
        File path = new File(uploadPath + File.separator + fileName);
        file.transferTo(path);
        return path.getAbsolutePath();
    }

    public byte[] viewImage(String fileName) throws IOException {
        File imgFile = new File(uploadPath + File.separator + fileName);
        if (!imgFile.exists()) {
            throw new IOException("File not found");
        }
        return Files.readAllBytes(Paths.get(imgFile.getAbsolutePath()));
    }
}

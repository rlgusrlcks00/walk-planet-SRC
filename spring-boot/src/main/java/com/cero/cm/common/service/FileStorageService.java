package com.cero.cm.common.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.PostConstruct;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

@Service
public class FileStorageService {

    private String uploadDir = "/usr/share/nginx/html/uploads/";

    @PostConstruct
    public void init() {
        if (uploadDir == null) {
            throw new IllegalArgumentException("Upload directory path is not set.");
        }

        // Ensure the upload directory ends with a file separator
        if (!uploadDir.endsWith("/") && !uploadDir.endsWith("\\")) {
            uploadDir += System.getProperty("file.separator");
        }

        // Create the directory if it doesn't exist
        try {
            Files.createDirectories(Paths.get(uploadDir));
        } catch (IOException e) {
            throw new IllegalArgumentException("Could not create upload directory. Please try again!", e);
        }
    }

    public String storeProfileFile(MultipartFile file) {
        if (file.isEmpty()) {
            return null;
        }

        System.out.println("=======================uploadDir : " + uploadDir);

        String fileName = System.currentTimeMillis() + "_" + file.getOriginalFilename();
        Path path = Paths.get(uploadDir + fileName);
        String baseUrl = "https://kichani.com/uploads/";
        String fileUrl = baseUrl + fileName;

        try {
            Files.copy(file.getInputStream(), path, StandardCopyOption.REPLACE_EXISTING);
        } catch (IOException e) {
            throw new IllegalArgumentException("Could not store file " + fileName + ". Please try again!", e);
        }
        return fileUrl;
    }
}

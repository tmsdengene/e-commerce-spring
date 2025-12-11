package com.gibesystems.ecommerce.shared;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.util.StringUtils;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

@Service
public class FileUploadService {
    private final String uploadDir = "uploads/products";

    public String uploadFile(MultipartFile file) throws IOException {
        if (file.isEmpty()) {
            throw new IOException("No file selected");
        }
        String fileName = StringUtils.cleanPath(file.getOriginalFilename());
        // Use absolute path relative to project root
        Path uploadPath = Paths.get("").toAbsolutePath().resolve(uploadDir);
        if (!Files.exists(uploadPath)) {
            Files.createDirectories(uploadPath);
        }
        Path filePath = uploadPath.resolve(fileName);
        file.transferTo(filePath.toFile());
        return "/uploads/products/" + fileName;
    }
}

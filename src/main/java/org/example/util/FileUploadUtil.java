package org.example.util;

import org.springframework.web.multipart.MultipartFile;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.*;
import java.util.UUID;

public class FileUploadUtil {

    // المسار الأساسي (Base Directory)
    private static final String BASE_DIR = "src/main/resources/static/";

    // ✅ الطريقة القديمة (كتبقى خدامة للسلع - كتمشي لـ images نيشان)
    public static String saveFile(MultipartFile file) throws IOException {
        return saveFile("images", file);
    }

    // ✅ الطريقة الجديدة (كتختار المجلد: logos, images...)
    public static String saveFile(String subDir, MultipartFile file) throws IOException {
        if (file.isEmpty()) {
            return null;
        }

        // تكوين المسار الكامل (static/logos أو static/images)
        Path uploadPath = Paths.get(BASE_DIR + subDir);

        if (!Files.exists(uploadPath)) {
            Files.createDirectories(uploadPath);
        }

        // إنشاء اسم فريد
        String fileName = UUID.randomUUID().toString() + "-" + file.getOriginalFilename();

        // الحفظ
        try (InputStream inputStream = file.getInputStream()) {
            Path filePath = uploadPath.resolve(fileName);
            Files.copy(inputStream, filePath, StandardCopyOption.REPLACE_EXISTING);
        }

        return fileName;
    }
}
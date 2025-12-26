package org.example.util;

import org.springframework.web.multipart.MultipartFile;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.UUID;

public class FileUploadUtil {

    // ✅ هنا فين غايتحطو التصاور أوتوماتيكياً
    // المسار: src/main/resources/static/images
    private static final String UPLOAD_DIR = "src/main/resources/static/images";

    public static String saveFile(MultipartFile file) throws IOException {
        if (file.isEmpty()) {
            return null;
        }

        // 1. تأكد أن المجلد كاين، وإلا صاوبو
        Path uploadPath = Paths.get(UPLOAD_DIR);
        if (!Files.exists(uploadPath)) {
            Files.createDirectories(uploadPath);
        }

        // 2. صاوب سمية فريدة (Unique) باش ما يتخلطوش التصاور
        // مثلاً: "image.jpg" -> "a1b2c-image.jpg"
        String fileName = UUID.randomUUID().toString() + "-" + file.getOriginalFilename();

        // 3. حط التصويرة فالمجلد
        try (InputStream inputStream = file.getInputStream()) {
            Path filePath = uploadPath.resolve(fileName);
            Files.copy(inputStream, filePath, StandardCopyOption.REPLACE_EXISTING);
        }

        // 4. رجع السمية باش SupplyService يخبيها فالداتاباز
        return fileName;
    }
}
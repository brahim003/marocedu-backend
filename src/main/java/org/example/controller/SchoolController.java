package org.example.controller;

import org.example.model.DTO.SchoolDTO;
import org.example.model.entity.School;
import org.example.service.SchoolService;
import org.example.util.FileUploadUtil;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

@RestController
@RequestMapping("/api/schools")
@CrossOrigin(origins = "http://localhost:5173")
public class SchoolController {

    private final SchoolService schoolService;

    public SchoolController(SchoolService schoolService) {
        this.schoolService = schoolService;
    }

    // --- 1. GET ALL ---
    @GetMapping
    public List<SchoolDTO> getAllSchools() {
        return schoolService.getAllSchools();
    }

    // --- 2. GET BY ID ---
    @GetMapping("/id/{id}")
    public SchoolDTO getSchoolById(@PathVariable Long id) {
        return schoolService.getSchoolById(id);
    }

    // --- 3. GET BY SLUG ---
    @GetMapping("/{slug}")
    public SchoolDTO getSchoolBySlug(@PathVariable String slug) {
        return schoolService.getSchoolBySlug(slug);
    }

    // --- 4. SERVE LOGO IMAGES (The Missing Fix) ---
    // ✅ هذه هي الدالة التي ستجعل الصور تظهر في الفرونت
    @GetMapping("/logo/{filename}")
    public ResponseEntity<Resource> getLogo(@PathVariable String filename) {
        try {
            // تأكد أن المسار يطابق المجلد الذي حددته في FileUploadUtil
            Path path = Paths.get("uploads/logos").resolve(filename);
            Resource resource = new UrlResource(path.toUri());

            if (resource.exists() || resource.isReadable()) {
                return ResponseEntity.ok()
                        .contentType(MediaType.IMAGE_PNG) // يدعم PNG و JPEG غالباً
                        .body(resource);
            } else {
                return ResponseEntity.notFound().build();
            }
        } catch (MalformedURLException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    // --- 5. CREATE SCHOOL ---
    @PostMapping
    public ResponseEntity<SchoolDTO> createSchool(
            @RequestParam("name") String name,
            @RequestParam("city") String city,
            @RequestParam("slug") String slug,
            @RequestParam(value = "image", required = false) MultipartFile multipartFile
    ) throws IOException {

        School school = new School();
        school.setName(name);
        school.setCity(city);
        school.setSlug(slug);

        if (multipartFile != null && !multipartFile.isEmpty()) {
            // حفظ الملف في مجلد logos
            String fileName = FileUploadUtil.saveFile("logos", multipartFile);
            school.setLogo(fileName);
        }

        SchoolDTO saved = schoolService.createSchool(school);
        return new ResponseEntity<>(saved, HttpStatus.CREATED);
    }

    // --- 6. DELETE ---
    @DeleteMapping("/id/{id}")
    public ResponseEntity<String> deleteSchool(@PathVariable Long id) {
        schoolService.deleteSchool(id);
        return ResponseEntity.ok("School deleted successfully!");
    }
}
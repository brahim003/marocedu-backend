package org.example.service;

import org.example.model.DTO.SchoolDTO;
import org.example.model.entity.Level;
import org.example.model.entity.School;
import org.example.repository.LevelRepository; // ✅ ضروري تكون كاينة
import org.example.repository.SchoolRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class SchoolService {

    @Autowired
    private SchoolRepository schoolRepository;

    @Autowired
    private LevelRepository levelRepository; // ✅ Injectina LevelRepository

    // --- 1. GET ALL ---
    public List<SchoolDTO> getAllSchools() {
        List<School> schoolEntities = schoolRepository.findAll();
        return schoolEntities.stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
    }

    // --- 2. GET BY ID ---
    public SchoolDTO getSchoolById(Long id) {
        School school = schoolRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("School not found"));
        return convertToDto(school);
    }

    // --- 3. GET BY SLUG ---
    public SchoolDTO getSchoolBySlug(String slug) {
        School school = schoolRepository.findBySlug(slug)
                .orElseThrow(() -> new RuntimeException("School not found"));
        return convertToDto(school);
    }

    // --- 4. CREATE SCHOOL (Automated Levels) ---
    public SchoolDTO createSchool(School school) {
        // 1. نسجلو المدرسة باش ناخدو ID ديالها
        School savedSchool = schoolRepository.save(school);

        // 2. نصاوبو المستويات أوتوماتيكياً لهاد المدرسة
        createStandardLevelsForSchool(savedSchool);

        // 3. نرجعو DTO
        return convertToDto(savedSchool);
    }

    // --- 5. DELETE ---
    public void deleteSchool(Long id) {
        if (!schoolRepository.existsById(id)) {
            throw new RuntimeException("School not found with id: " + id);
        }
        schoolRepository.deleteById(id);
    }

    // --- 6. HELPER METHODS ---

    private SchoolDTO convertToDto(School entity) {
        SchoolDTO dto = new SchoolDTO();
        dto.setId(entity.getId());
        dto.setName(entity.getName());
        dto.setSlug(entity.getSlug());
        dto.setLogo(entity.getLogo());
        dto.setCity(entity.getCity());
        return dto;
    }

    // ✅ الدالة السحرية اللي كتصاوب المستويات
    private void createStandardLevelsForSchool(School school) {
        List<Level> levels = new ArrayList<>();

        // --- Maternelle ---
        levels.add(new Level(null, "Petite Section", "ps", "maternelle", school));
        levels.add(new Level(null, "Moyenne Section", "ms", "maternelle", school));
        levels.add(new Level(null, "Grande Section", "gs", "maternelle", school));

        // --- Primaire ---
        levels.add(new Level(null, "CP", "cp", "primaire", school));
        levels.add(new Level(null, "CE1", "ce1", "primaire", school));
        levels.add(new Level(null, "CE2", "ce2", "primaire", school));
        levels.add(new Level(null, "CM1", "cm1", "primaire", school));
        levels.add(new Level(null, "CM2", "cm2", "primaire", school));
        levels.add(new Level(null, "6ème Année", "6e", "primaire", school));

        // --- Collège ---
        levels.add(new Level(null, "1ère Année Collège", "1ac", "college", school));
        levels.add(new Level(null, "2ème Année Collège", "2ac", "college", school));
        levels.add(new Level(null, "3ème Année Collège", "3ac", "college", school));

        // --- Lycée ---
        levels.add(new Level(null, "Tronc Commun", "tc", "lycee", school));
        levels.add(new Level(null, "1ère Bac", "1bac", "lycee", school));
        levels.add(new Level(null, "2ème Bac", "2bac", "lycee", school));

        // تسجيل الكل دفعة واحدة
        levelRepository.saveAll(levels);
    }
}
package org.example.service;

import org.example.model.DTO.SchoolDTO; // L import dialk s7i7
import org.example.model.entity.Level; // Ghadi n7tajoha f createSchool
import org.example.model.entity.School;
import org.example.repository.SchoolRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors; // Ghadi nsta3mlo stream hna 7it 7ssan

@Service
public class SchoolService {

    @Autowired
    private SchoolRepository schoolRepository;

    // --- 1. L METHOD DIAL "GET ALL" (B DTO) ---
    // Khallina ghir l method li kay rje3 DTO
    public List<SchoolDTO> getAllSchools() {
        // Jib l Entities
        List<School> schoolEntities = schoolRepository.findAll();

        // 7awelhom l DTOs (b tari9a n9ia b streams)
        return schoolEntities.stream()
                .map(this::convertToDto) // Katsta3mel l "helper method" li l te7t
                .collect(Collectors.toList());
    }

    // --- 2. L METHOD DIAL "GET BY ID" (B DTO) ---
    // Tbedel l return type l SchoolDTO
    public SchoolDTO getSchoolById(Long id) {
        // Jib l entity 3adi
        School school = schoolRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("School not found"));

        // 7awel l entity l DTO 3ad rj3o
        return convertToDto(school);
    }

    // --- 3. L METHOD DIAL "GET BY SLUG" (B DTO) ---
    // Tbedel l return type l SchoolDTO
    public SchoolDTO getSchoolBySlug(String slug) {
        // Jib l entity 3adi
        School school = schoolRepository.findBySlug(slug)
                .orElseThrow(() -> new RuntimeException("School not found"));

        // 7awel l entity l DTO 3ad rj3o
        return convertToDto(school);
    }

    // --- 4. L METHOD DIAL "CREATE" (B DTO) ---
    // Tbedel l return type l SchoolDTO
    public SchoolDTO createSchool(School school) { // (L 7al l 7ssan howa t 9bel DTO, walakin hada ykhdem)

        // L code dialk dial l levels s7i7
        if (school.getLevels() != null) {
            for (Level level : school.getLevels()) {
                level.setSchool(school);
            }
        }

        // 1. Ssjjel l entity f database
        School savedSchool = schoolRepository.save(school);

        // 2. 7awel l entity li yalah tssjlat l DTO o rj3ha
        return convertToDto(savedSchool);
    }

    // --- 5. L METHOD DIAL "DELETE" (MABDALCH) ---
    // Hada mzyan 7it kay rje3 void (walo)
    public void deleteSchool(Long id) {
        if (!schoolRepository.existsById(id)) {
            throw new RuntimeException("School not found with id: " + id);
        }
        schoolRepository.deleteById(id);
    }

    // --- 6. L "HELPER" METHOD (L M3AWN) ---
    // Had l method 9adnah bach manb9awch n 3awdo l code dial t7wil
    // "private" 7it ghadi ysta3mel ghir l wast dial had l class
    private SchoolDTO convertToDto(School entity) {
        SchoolDTO dto = new SchoolDTO();
        dto.setId(entity.getId());
        dto.setName(entity.getName());
        dto.setSlug(entity.getSlug());
        dto.setLogo(entity.getLogo());
        dto.setCity(entity.getCity());
        return dto;
    }
}
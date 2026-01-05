package org.example.service;

import org.example.model.DTO.LevelDTO;
import org.example.model.entity.Level;
import org.example.repository.LevelRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class LevelService {

    @Autowired
    private LevelRepository levelRepository;

    // --- 1. GET levels by school slug ---
    public List<LevelDTO> getLevelsBySchoolSlug(String slug) {
        List<Level> levels = levelRepository.findBySchoolSlug(slug);

        return levels.stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
    }

    // --- Helper method: Level → LevelDTO ---
    private LevelDTO convertToDto(Level level) {
        // ✅ الحل: خوذ الـ Cycle نيشان من الداتاباز بلا ما تحسبو
        // بما أن SchoolService ديجا عمراتو صحيح
        return new LevelDTO(level.getName(), level.getSlug(), level.getCycle());
    }

    // 🗑️ مسحت getCycleFromSlug حيت مابقاش عندنا بيها غرض
}
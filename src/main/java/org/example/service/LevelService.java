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
        // Fetch levels directly using school slug
        List<Level> levels = levelRepository.findBySchoolSlug(slug);

        // If no levels, just return empty list
        return levels.stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
    }


    // --- Helper method: Level → LevelDTO ---
    private LevelDTO convertToDto(Level level) {
        String cycle = getCycleFromSlug(level.getSlug());
        return new LevelDTO(level.getName(), level.getSlug(), cycle);
    }

    // --- Determine cycle from level slug ---
    private String getCycleFromSlug(String slug) {
        switch (slug) {
            case "ps":
            case "ms":
            case "gs":
                return "maternelle";
            case "cp":
            case "ce1":
            case "ce2":
            case "cm1":
            case "cm2":
            case "6e":
                return "primaire";
            case "5e":
            case "4e":
            case "3e":
                return "college";
            case "2nde":
            case "1bac":
            case "tale":
                return "lycee";
            default:
                return "unknown";
        }
    }
}

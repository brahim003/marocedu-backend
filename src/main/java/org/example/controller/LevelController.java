package org.example.controller;

import org.example.model.DTO.LevelDTO;
import org.example.service.LevelService;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.ResponseEntity;
import org.springframework.http.HttpStatus;

import java.util.List;

@RestController
@RequestMapping("/api/levels")
public class LevelController {

    private final LevelService levelService;

    public LevelController(LevelService levelService) {
        this.levelService = levelService;
    }

    // GET all levels by school slug
    @GetMapping("/by-school/{schoolSlug}")
    public ResponseEntity<List<LevelDTO>> getLevelsBySchool(@PathVariable String schoolSlug) {
        List<LevelDTO> levels = levelService.getLevelsBySchoolSlug(schoolSlug);

        if (levels.isEmpty()) {
            // Return 204 No Content if no levels are found
            return ResponseEntity.noContent().build();
        }

        // Return 200 OK with the list of levels
        return ResponseEntity.ok(levels);
    }

    // Optional: GET level by slug (future use)
    // @GetMapping("/{slug}")
    // public ResponseEntity<LevelDTO> getLevelBySlug(@PathVariable String slug) {
    //     LevelDTO level = levelService.getLevelBySlug(slug);
    //     if (level == null) {
    //         return ResponseEntity.notFound().build();
    //     }
    //     return ResponseEntity.ok(level);
    // }

    // Optional: CREATE new level (future use)
    // @PostMapping
    // public ResponseEntity<LevelDTO> createLevel(@RequestBody Level level) {
    //     LevelDTO saved = levelService.createLevel(level);
    //     return new ResponseEntity<>(saved, HttpStatus.CREATED);
    // }

    // Optional: DELETE level (future use)
    // @DeleteMapping("/{id}")
    // public ResponseEntity<String> deleteLevel(@PathVariable Long id) {
    //     levelService.deleteLevel(id);
    //     return ResponseEntity.ok("Level deleted successfully!");
    // }
}

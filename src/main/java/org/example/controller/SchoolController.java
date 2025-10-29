package org.example.controller;

import org.example.model.DTO.SchoolDTO;
import org.example.model.entity.School;
import org.example.service.SchoolService;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.ResponseEntity;
import org.springframework.http.HttpStatus;
import java.util.List;

@RestController
@RequestMapping("/api/schools") // hadi ktgol l Spring ana aya ga3 les path atbda b had /api/schools
public class SchoolController {

    private final SchoolService schoolService;

    public SchoolController(SchoolService schoolService) {
        this.schoolService = schoolService;
    }

    // GET all schools
    @GetMapping
    public List<SchoolDTO> getAllSchools() {
        return schoolService.getAllSchools();
    }

    // GET school by ID
    @GetMapping("/id/{id}")
    public SchoolDTO getSchoolById(@PathVariable Long id) {
        return schoolService.getSchoolById(id);
    }

    // GET school by slug
    @GetMapping("/{slug}")
    public SchoolDTO getSchoolBySlug(@PathVariable String slug) {
        return schoolService.getSchoolBySlug(slug);
    }

    // CREATE school
    @PostMapping
    public ResponseEntity<SchoolDTO> createSchool(@RequestBody School school) {
        SchoolDTO saved = schoolService.createSchool(school);
        return new ResponseEntity<>(saved, HttpStatus.CREATED);
    }

    // DELETE school
    @DeleteMapping("/id/{id}")
    public ResponseEntity<String> deleteSchool(@PathVariable Long id) {
        schoolService.deleteSchool(id);
        return ResponseEntity.ok("School deleted successfully!");
    }

}

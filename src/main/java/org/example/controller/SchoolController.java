package org.example.controller;

import org.example.model.entity.School;
import org.example.service.SchoolService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/schools") // hadi ktgol l sprin ana aya ga3 les path atbda b had api/schools
public class SchoolController {

    private final SchoolService schoolService;

    //
    public SchoolController(SchoolService schoolService) {
        this.schoolService = schoolService;
    }

    // hada madrtx fih path dila url hint automatique kiyakhed requestmpping(api/schools par defaut)
    @GetMapping
    public List<School> getAllSchools() {
        return schoolService.getAllSchools();
    }

    //  Get school by ID (: http://localhost:8080/api/schools/id/1) ila xfti hna rah tzad requestmapping  api/school ela getmapping
    @GetMapping("/id/{id}")
    public School getSchoolById(@PathVariable Long id) {
        return schoolService.getSchoolById(id);
    }

    //  Get school by slug (: http://localhost:8080/api/schools/lycee-mohammed-vi)
    @GetMapping("/{slug}")
    public School getSchoolBySlug(@PathVariable String slug) {
        return schoolService.getSchoolBySlug(slug);
    }
}

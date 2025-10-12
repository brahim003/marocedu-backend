package org.example.service;

import org.example.model.entity.School;
import org.example.repository.SchoolRepository;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import java.util.Optional;


import java.util.List;

@Service
public class SchoolService {

    private final SchoolRepository schoolRepository;

    public SchoolService(SchoolRepository schoolRepository) {
        this.schoolRepository = schoolRepository;
    }

    // hna bax njibo ga3 shcools mratbin
    public List<School> getAllSchools() {
        return schoolRepository.findAll(
                Sort.by(
                        Sort.Order.desc("isPartner"),
                        Sort.Order.asc("name")
                )
        );
    }

    // njibo madrasa b id dialha
    public School getSchoolById(Long id) {
        return schoolRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("School not found"));
    }

    // njibo madrasa b slug (slug howa fhal smia wlakin bla space bax fax ndiroha f url maykonox fiha space o dir lina mxkl)
    public School getSchoolBySlug(String slug) {
        return schoolRepository.findBySlug(slug)
                .orElseThrow(() -> new RuntimeException("School not found"));
    }
}

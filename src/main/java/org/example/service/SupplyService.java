package org.example.service;

import org.example.model.DTO.OptionDTO;
import org.example.model.DTO.OptionRequestDTO;
import org.example.model.DTO.SupplyDTO;
import org.example.model.DTO.SupplyRequestDTO;
import org.example.model.entity.Level;
import org.example.model.entity.Option;
import org.example.model.entity.School;
import org.example.model.entity.Supply;
import org.example.repository.LevelRepository;
import org.example.repository.OptionRepository;
import org.example.repository.SchoolRepository;
import org.example.repository.SupplyRepository;
import org.example.util.FileUploadUtil;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class SupplyService {

    private final SupplyRepository supplyRepository;
    private final OptionService optionService;
    private final SchoolRepository schoolRepository;
    private final LevelRepository levelRepository;
    private final OptionRepository optionRepository;

    public SupplyService(SupplyRepository supplyRepository,
                         OptionService optionService,
                         SchoolRepository schoolRepository,
                         LevelRepository levelRepository,
                         OptionRepository optionRepository) {
        this.supplyRepository = supplyRepository;
        this.optionService = optionService;
        this.schoolRepository = schoolRepository;
        this.levelRepository = levelRepository;
        this.optionRepository = optionRepository;
    }

    // =================================================================
    // 🆕 PARTIE ÉCRITURE : AJOUTER UNE SL3A (POST)
    // =================================================================

    @Transactional
    public Supply createSupplyWithImage(SupplyRequestDTO request, MultipartFile imageFile) throws IOException {

        // 1. Check Relations
        Level level = levelRepository.findById(request.getLevelId())
                .orElseThrow(() -> new RuntimeException("Level not found ID: " + request.getLevelId()));

        School school = schoolRepository.findById(request.getSchoolId())
                .orElseThrow(() -> new RuntimeException("School not found ID: " + request.getSchoolId()));

        // 2. Image Upload
        String imageFileName = null;
        if (imageFile != null && !imageFile.isEmpty()) {
            imageFileName = FileUploadUtil.saveFile(imageFile);
        }

        // 3. Mapping
        Supply supply = new Supply();
        supply.setName(request.getName());
        supply.setDescription(request.getDescription());
        supply.setPrice(request.getPrice());
        supply.setImage(imageFileName);

        supply.setIsbn(request.getIsbn());
        supply.setMarque(request.getMarque());
        supply.setPosition(request.getPosition());
        supply.setCurrency(request.getCurrency() != null ? request.getCurrency() : "MAD");
        supply.setIsBook(request.getIsBook() != null ? request.getIsBook() : false);
        supply.setStockQuantity(request.getStockQuantity() != null ? request.getStockQuantity() : 0);

        // ✅ Relation Singular (ManyToOne)
        supply.setLevel(level);
        supply.setSchool(school);

        supply.setInStock(supply.getStockQuantity() > 0);

        // 4. Save Parent
        supply = supplyRepository.save(supply);

        // 5. Save Options
        if (request.getOptions() != null && !request.getOptions().isEmpty()) {
            for (OptionRequestDTO optDto : request.getOptions()) {
                Option option = new Option();
                option.setName(optDto.getOptionName());
                option.setStockQuantity(optDto.getStockQuantity());
                option.setSupply(supply);

                optionRepository.save(option);
            }
            supply.setInStock(true);
            supplyRepository.save(supply);
        }

        return supply;
    }

    // =================================================================
    // 🆕 NOUVELLES MÉTHODES POUR ADMIN PANEL
    // =================================================================

    // ✅ 1. Get Supplies by Level ID
    public List<SupplyDTO> getSuppliesByLevelId(Long levelId) {
        // تم تصحيح الاسم هنا ليتوافق مع الـ Repository
        // findByLevel_Id بدل findByLevels_Id
        List<Supply> supplies = supplyRepository.findByLevel_Id(levelId);

        return supplies.stream()
                .map(this::convertToSupplyDTO)
                .collect(Collectors.toList());
    }

    // ✅ 2. Delete Supply
    public void deleteSupply(Long id) {
        if (!supplyRepository.existsById(id)) {
            throw new RuntimeException("Supply not found with id: " + id);
        }
        supplyRepository.deleteById(id);
    }

    // =================================================================
    // 📖 PARTIE LECTURE (GET)
    // =================================================================

    public List<SupplyDTO> getSuppliesBySchoolAndLevel(String schoolSlug, String levelSlug) {
        List<Supply> supplies = supplyRepository.findByLevelSchoolSlugAndLevelSlug(schoolSlug, levelSlug);
        return supplies.stream()
                .map(this::convertToSupplyDTO)
                .collect(Collectors.toList());
    }

    public SupplyDTO getSupplyById(Long id) {
        Supply supply = supplyRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Supply not found with id: " + id));
        return convertToSupplyDTO(supply);
    }

    // --- Helper: Entity -> DTO ---
    private SupplyDTO convertToSupplyDTO(Supply supply) {
        SupplyDTO dto = new SupplyDTO();

        dto.setId(supply.getId());
        dto.setName(supply.getName());
        dto.setPrice(supply.getPrice());
        dto.setCurrency(supply.getCurrency());
        dto.setInStock(supply.getInStock());
        dto.setImage(supply.getImage());

        dto.setMarque(supply.getMarque());
        dto.setPosition(supply.getPosition());
        dto.setIsBook(supply.getIsBook());

        // Slug Mapping
        if (supply.getSchool() != null) {
            dto.setSchool(supply.getSchool().getSlug());
        } else if (supply.getLevel() != null && supply.getLevel().getSchool() != null) {
            dto.setSchool(supply.getLevel().getSchool().getSlug());
        }

        if (supply.getLevel() != null) {
            dto.setLevel(supply.getLevel().getSlug());
        }

        // Options Mapping
        if (supply.getOptions() != null) {
            List<OptionDTO> optionDTOs = supply.getOptions().stream()
                    .map(optionService::convertToOptionDTO)
                    .collect(Collectors.toList());
            dto.setOptions(optionDTOs);
        }

        return dto;
    }
}